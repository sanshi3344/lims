package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Conversation {

    // Constants
    public static final String KEY_CONVERSATION_ID = "roomJID";
    public static final String KEY_CONVERSATION_TYPE = "conversationType";
    public static final String KEY_MESSAGES = "messages";

    // Properties
    private String conversationId;
    private ConversationType conversationType;
    private List<Buddy> participants;
    private String title;
    private String name;
    private String type;
    private String visibility;
    private Message lastMessage;
    private List<Message> messages;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new Conversation from the PollerRequest
     *
     * @param pollerRequest request
     * @return Conversation
     */
    public static Conversation fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new conversation
        Conversation conversation = new Conversation();
        // Conversation Id
        if (parameterMap.containsKey(KEY_CONVERSATION_ID)) {
            conversation.conversationId = GetterUtil.getString(parameterMap.get(KEY_CONVERSATION_ID));
        }
        // Conversation type
        if (parameterMap.containsKey(KEY_CONVERSATION_TYPE)) {
            String key = GetterUtil.getString(parameterMap.get(KEY_CONVERSATION_TYPE));
            conversation.conversationType = ConversationType.fromKey(key);
        }

        return conversation;
    }

    /**
     * Factory method which creates new list of Conversations from the list of ConversationDetails
     *
     * @param detailsList list of conversation details
     * @return List<Conversation> of conversations
     */
    public static List<Conversation> fromConversationDetails(List<ConversationDetails> detailsList) {
        // Create new list of conversations
        List<Conversation> conversations = new ArrayList<Conversation>();

        // Iterate through details and create conversation based on that
        for (ConversationDetails details : detailsList) {
            conversations.add(Conversation.fromConversationDetails(details));
        }

        return conversations;
    }

    /**
     * Factory method which creates new Conversation from the ConversationDetails
     *
     * @param details conversation details
     * @return Conversation
     */
    public static Conversation fromConversationDetails(ConversationDetails details) {
        // Create new conversation
        Conversation conversation = new Conversation();
        // Map parameters
        conversation.conversationId = details.getConversationId();
        conversation.title = details.getTitle();
        conversation.name = details.getName();
        conversation.type = details.getType();
        conversation.visibility = details.getVisibility();

        // Relations
        if (details.getLastMessage() != null) {
            conversation.lastMessage = Message.fromMessageDetails(details.getLastMessage());
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetails(details.getMessages());
        }

        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        return conversation;
    }

    /**
     * Maps conversation to conversation details
     *
     * @return ConversationDetails
     */
    public ConversationDetails toConversationDetails() {
        // Create conversation details
        ConversationDetails details = new ConversationDetails();
        // Map data from conversation
        details.setConversationId(conversationId);

        // Relations
        if (conversationType != null) {
            details.setConversationType(conversationType.toConversationTypeDetails());
        }

        if (participants != null) {
            List<BuddyDetails> participantDetails = new LinkedList<BuddyDetails>();
            for (Buddy participant : participants) {
                participantDetails.add(participant.toBuddyDetails());
            }
            details.setParticipants(participantDetails);
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
    }

    public List<Buddy> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Buddy> participants) {
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId='" + conversationId + '\'' +
                ", conversationType=" + conversationType +
                ", participants=" + participants +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", visibility='" + visibility + '\'' +
                ", lastMessage=" + lastMessage +
                ", messages=" + messages +
                '}';
    }
}
