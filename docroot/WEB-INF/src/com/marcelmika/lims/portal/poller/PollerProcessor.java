package com.marcelmika.lims.portal.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.poller.PollerResponseClosedException;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.api.events.settings.*;
import com.marcelmika.lims.core.service.*;
import com.marcelmika.lims.portal.domain.*;

import java.util.List;

/**
 * Receives and sends messages to the Liferay frontend javascript later where is periodically consumed.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PollerProcessor extends BasePollerProcessor {

    // Log
    private static Log log = LogFactoryUtil.getLog(PollerProcessor.class);

    // Dependencies
    BuddyCoreService buddyCoreService = BuddyCoreServiceUtil.getBuddyCoreService();
    GroupCoreService groupCoreService = GroupCoreServiceUtil.getGroupCoreService();
    ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();


    /**
     * This method is called whenever the user calls submitRequest(portletId, data, key) method in
     * browser (used in Liferay.Chat.Poller.js).
     * Unfortunately, this method does not return any value. As a result, there is no way
     * to confirm success or failure of the given request because no response (!) is received.
     * Consequently, there is no other way to receive data from the backend besides waiting
     * for the response of the receive action (i.e. waiting for the result of doReceive() method).
     *
     * @param pollerRequest sent via the submitRequest(portletId, data, key) method from browser
     * @throws IllegalArgumentException if request does not contain chunkID used to determine request type
     */
    @Override
    protected void doSend(PollerRequest pollerRequest) throws IllegalArgumentException {
        // Poller dispatcher will call appropriate method on poller processor
        ResponseEvent responseEvent = PollerDispatcher.dispatchSendRequest(pollerRequest, this);

        // Log info
        if (responseEvent != null) {
            log.info(responseEvent.getResult());
        }
    }

    /**
     * This method serves as a request/response gateway. However, it cannot be explicitly triggered.
     * Liferay has its own timer, which repeats the given operation for each allotted amount of time.
     * This method is periodically called from the browser (called in Liferay.Chat.Poller.js).
     *
     * @param pollerRequest  sent periodically from browser
     * @param pollerResponse provided response
     * @throws Exception
     */
    @Override
    protected void doReceive(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
        // Initial request is the first request sent to the poller (i.e. whenever the user opens any page).
        // If the user moves to another page within the portal, new initial request is sent.
        if (pollerRequest.isInitialRequest()) {
            // Make polling faster on the initial request
            pollerResponse.setParameter(PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY, Boolean.TRUE.toString());
        }

        // Poller dispatcher will call appropriate methods on poller processor scheduled for
        // the do receive event
        PollerDispatcher.dispatchReceiveRequest(pollerRequest, pollerResponse, this);
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Buddy Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Update buddy's status
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent updateStatus(PollerRequest pollerRequest) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return buddyCoreService.updatePresence(new UpdatePresenceBuddyRequestEvent(
                        buddy.getBuddyId(), buddy.getPresence().toPresenceDetails())
        );
    }


    /**
     * // ---------------------------------------------------------------------------------------------------------
     * //   Group Lifecycle
     * // ---------------------------------------------------------------------------------------------------------
     * <p/>
     * /**
     * Fetches all groups related to the buddy.
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     */
    protected void getGroupList(PollerRequest pollerRequest, PollerResponse pollerResponse) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Get groups request
        GetGroupsResponseEvent responseEvent = groupCoreService.getGroups(
                new GetGroupsRequestEvent(buddy.toBuddyDetails())
        );


        if (responseEvent.isSuccess()) {
            // Get groups from group details
//            List<Group> groups = Group.fromGroupDetails(responseEvent.getGroups());
            // Serialize to json string (include buddies collection)
//            String jsonString = JSONFactoryUtil.looseSerialize(groups, Group.KEY_BUDDIES);

//            try {
            // Add to response
//                pollerResponse.setParameter("groups", createJsonArray(jsonString));
//            } catch (PollerResponseClosedException e) {
            // Log what went wrong
            log.error(responseEvent.getException());
//            }

        } else {
            // At least log what went wrong
            log.error(responseEvent.getException());
        }
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Conversation Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Fetches all conversations related to the particular user
     * Returns conversations without messages
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     */
    protected void getAllConversations(PollerRequest pollerRequest, PollerResponse pollerResponse) {
        // Create buddy
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Get conversations request
        GetConversationsResponseEvent responseEvent = conversationCoreService.getConversations(
                new GetConversationsRequestEvent(buddy.toBuddyDetails())
        );

        if (responseEvent.isSuccess()) {
            // Get conversations from conversation details
            List<Conversation> conversation = Conversation.fromConversationDetails(responseEvent.getConversations());
            // Serialize to json
            String jsonString = JSONFactoryUtil.looseSerialize(conversation);

            // Add to response
            try {
                pollerResponse.setParameter("conversations", createJsonArray(jsonString));
            } catch (PollerResponseClosedException e) {
                log.error("Poller response was closed", e);
            }

        } else {
            // At least log what went wrong
            log.error(responseEvent.getException());
        }
    }

    /**
     * Fetches all conversations which were opened by the user. Returns full
     * conversation with all messages
     * Note: all message should be replaced with the start and limit size of the feed
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     */
    protected void getOpenedConversations(PollerRequest pollerRequest, PollerResponse pollerResponse) {
        // Create buddy from request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Get opened conversations request
        GetOpenedConversationsResponseEvent responseEvent = conversationCoreService.getOpenedConversations(
                new GetOpenedConversationsRequestEvent(buddy.toBuddyDetails())
        );


        if (responseEvent.isSuccess()) {
            // Serialize to json string
            String jsonString = JSONFactoryUtil.looseSerialize(responseEvent.getConversations());

            // Add to response
            try {
                pollerResponse.setParameter("openedConversations", createJsonArray(jsonString));
            } catch (PollerResponseClosedException e) {
                log.error("Poller response was closed", e);
            }

        } else {
            // At least log what went wrong
            log.error(responseEvent.getException());
        }
    }


    /**
     * Creates conversation from a list of buddies involved in the conversation and an initial message
     * todo: rename to createMultiUserConversation
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent createConversation(PollerRequest pollerRequest) {
        // Create objects from poller request
        Message message = Message.fromPollerRequest(pollerRequest);
        BuddyCollection buddies = BuddyCollection.fromPollerRequest(pollerRequest);

        // Send request to core service
//        return conversationCoreService.createConversation(new CreateConversationRequestEvent(
//                        buddies.toBuddyCollectionDetails(), message.toMessageDetails())
//        );
        return null;
    }


    /**
     * Creates single user conversation with a buddy selected in request
     *
     * @param pollerRequest PollerRequest
     * @return Response Event
     */
    protected ResponseEvent createSingleUserConversation(PollerRequest pollerRequest) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);

        log.info("BUDDY TO CHAT: " + buddy.getScreenName());

        // Send request to core service
        return null;
    }

    /**
     * Open conversation for the particular buddy
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent openConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy from the poller request
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.openConversation(new OpenConversationRequestEvent(
                        buddy.getBuddyId(), conversation.getConversationId())
        );
    }

    /**
     * Close conversation for the particular buddy
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent closeConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy from poller request
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.closeConversation(new CloseConversationRequestEvent(
                buddy.getBuddyId(), conversation.getConversationId()
        ));
    }

    /**
     * Leave conversation for the particular buddy
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent leaveConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy from poller request
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.leaveConversation(new LeaveConversationRequestEvent(
                buddy.getBuddyId(), conversation.getConversationId()
        ));
    }

    /**
     * Add a list of buddies to the conversation
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent addToConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy collection
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        BuddyCollection buddies = BuddyCollection.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.addBuddies(new AddBuddiesRequestEvent(
                buddies.toBuddyCollectionDetails(), conversation.toConversationDetails()
        ));
    }

    /**
     * Sends message in conversation
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent sendMessage(PollerRequest pollerRequest) {
        // Create buddy, conversation and message
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Message message = Message.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.sendMessage(new SendMessageRequestEvent(
                buddy.toBuddyDetails(), conversation.toConversationDetails(), message.toMessageDetails()
        ));
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Settings Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Update buddy's settings
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent updateSettings(PollerRequest pollerRequest) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return settingsCoreService.updateSettings(new UpdateSettingsRequestEvent(
                        buddy.getBuddyId(), buddy.getSettings().toSettingsDetails())
        );
    }

    /**
     * Update buddy's active panel
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent updateActivePanel(PollerRequest pollerRequest) {
        // Create buddy and settings from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        Settings settings = Settings.fromPollerRequest(pollerRequest);
        // Send request to core service
        return settingsCoreService.updateActivePanel(new UpdateActivePanelRequestEvent(
                buddy.getBuddyId(), settings.getActivePanelId()
        ));
    }

    /**
     * Enable or disable chat for the buddy
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent setChatEnabled(PollerRequest pollerRequest) {
        // Create buddy
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Check if the chat should be enabled or disabled
        boolean enabled = getBoolean(pollerRequest, "enabled");
        // Enable or disable chat
        if (enabled) {
            return settingsCoreService.enableChat(new EnableChatRequestEvent(buddy.toBuddyDetails()));
        } else {
            return settingsCoreService.disableChat(new DisableChatRequestEvent(buddy.toBuddyDetails()));
        }
    }


    // ------------------------------------------------------------------------------
    //   Helpers
    // ------------------------------------------------------------------------------

    /**
     * Creates JSON array from JSON string. Avoids exception so if there is any error returns null.
     *
     * @param jsonString serialized string
     * @return JSONArray
     */
    private JSONArray createJsonArray(String jsonString) {

        JSONArray jsonArray = null;

        try {
            jsonArray = JSONFactoryUtil.createJSONArray(jsonString);
        } catch (JSONException e) {
            // At least log what went wrong
            log.error(e);
        }

        return jsonArray;
    }

    /**
     * Creates JSON object from JSON string. Avoids exception so if there is any error returns null.
     *
     * @param jsonString serialized string
     * @return JSONObject
     */
    private JSONObject createJsonObject(String jsonString) {

        JSONObject jsonObject = null;

        try {
            jsonObject = JSONFactoryUtil.createJSONObject(jsonString);
        } catch (JSONException e) {
            // At least log what went wrong
            log.error(e);
        }

        return jsonObject;
    }
}