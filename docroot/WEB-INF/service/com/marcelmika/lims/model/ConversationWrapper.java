/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.marcelmika.lims.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Conversation}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Conversation
 * @generated
 */
public class ConversationWrapper implements Conversation,
	ModelWrapper<Conversation> {
	public ConversationWrapper(Conversation conversation) {
		_conversation = conversation;
	}

	public Class<?> getModelClass() {
		return Conversation.class;
	}

	public String getModelClassName() {
		return Conversation.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cid", getCid());
		attributes.put("conversationId", getConversationId());
		attributes.put("conversationType", getConversationType());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		String conversationId = (String)attributes.get("conversationId");

		if (conversationId != null) {
			setConversationId(conversationId);
		}

		String conversationType = (String)attributes.get("conversationType");

		if (conversationType != null) {
			setConversationType(conversationType);
		}
	}

	/**
	* Returns the primary key of this conversation.
	*
	* @return the primary key of this conversation
	*/
	public long getPrimaryKey() {
		return _conversation.getPrimaryKey();
	}

	/**
	* Sets the primary key of this conversation.
	*
	* @param primaryKey the primary key of this conversation
	*/
	public void setPrimaryKey(long primaryKey) {
		_conversation.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the cid of this conversation.
	*
	* @return the cid of this conversation
	*/
	public long getCid() {
		return _conversation.getCid();
	}

	/**
	* Sets the cid of this conversation.
	*
	* @param cid the cid of this conversation
	*/
	public void setCid(long cid) {
		_conversation.setCid(cid);
	}

	/**
	* Returns the conversation ID of this conversation.
	*
	* @return the conversation ID of this conversation
	*/
	public java.lang.String getConversationId() {
		return _conversation.getConversationId();
	}

	/**
	* Sets the conversation ID of this conversation.
	*
	* @param conversationId the conversation ID of this conversation
	*/
	public void setConversationId(java.lang.String conversationId) {
		_conversation.setConversationId(conversationId);
	}

	/**
	* Returns the conversation type of this conversation.
	*
	* @return the conversation type of this conversation
	*/
	public java.lang.String getConversationType() {
		return _conversation.getConversationType();
	}

	/**
	* Sets the conversation type of this conversation.
	*
	* @param conversationType the conversation type of this conversation
	*/
	public void setConversationType(java.lang.String conversationType) {
		_conversation.setConversationType(conversationType);
	}

	public boolean isNew() {
		return _conversation.isNew();
	}

	public void setNew(boolean n) {
		_conversation.setNew(n);
	}

	public boolean isCachedModel() {
		return _conversation.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_conversation.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _conversation.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _conversation.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_conversation.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _conversation.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_conversation.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ConversationWrapper((Conversation)_conversation.clone());
	}

	public int compareTo(com.marcelmika.lims.model.Conversation conversation) {
		return _conversation.compareTo(conversation);
	}

	@Override
	public int hashCode() {
		return _conversation.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.model.Conversation> toCacheModel() {
		return _conversation.toCacheModel();
	}

	public com.marcelmika.lims.model.Conversation toEscapedModel() {
		return new ConversationWrapper(_conversation.toEscapedModel());
	}

	public com.marcelmika.lims.model.Conversation toUnescapedModel() {
		return new ConversationWrapper(_conversation.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _conversation.toString();
	}

	public java.lang.String toXmlString() {
		return _conversation.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_conversation.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ConversationWrapper)) {
			return false;
		}

		ConversationWrapper conversationWrapper = (ConversationWrapper)obj;

		if (Validator.equals(_conversation, conversationWrapper._conversation)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Conversation getWrappedConversation() {
		return _conversation;
	}

	public Conversation getWrappedModel() {
		return _conversation;
	}

	public void resetOriginalValues() {
		_conversation.resetOriginalValues();
	}

	private Conversation _conversation;
}