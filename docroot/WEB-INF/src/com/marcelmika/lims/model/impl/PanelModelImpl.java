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

package com.marcelmika.lims.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import com.marcelmika.lims.model.Panel;
import com.marcelmika.lims.model.PanelModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the Panel service. Represents a row in the &quot;LiferayLIMS_Panel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.marcelmika.lims.model.PanelModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PanelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PanelImpl
 * @see com.marcelmika.lims.model.Panel
 * @see com.marcelmika.lims.model.PanelModel
 * @generated
 */
public class PanelModelImpl extends BaseModelImpl<Panel> implements PanelModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a panel model instance should use the {@link com.marcelmika.lims.model.Panel} interface instead.
	 */
	public static final String TABLE_NAME = "LiferayLIMS_Panel";
	public static final Object[][] TABLE_COLUMNS = {
			{ "pid", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "activePanelId", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table LiferayLIMS_Panel (pid LONG not null primary key,userId LONG,activePanelId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table LiferayLIMS_Panel";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.marcelmika.lims.model.Panel"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.marcelmika.lims.model.Panel"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.marcelmika.lims.model.Panel"),
			true);
	public static long USERID_COLUMN_BITMASK = 1L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.marcelmika.lims.model.Panel"));

	public PanelModelImpl() {
	}

	public long getPrimaryKey() {
		return _pid;
	}

	public void setPrimaryKey(long primaryKey) {
		setPid(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_pid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return Panel.class;
	}

	public String getModelClassName() {
		return Panel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("pid", getPid());
		attributes.put("userId", getUserId());
		attributes.put("activePanelId", getActivePanelId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pid = (Long)attributes.get("pid");

		if (pid != null) {
			setPid(pid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String activePanelId = (String)attributes.get("activePanelId");

		if (activePanelId != null) {
			setActivePanelId(activePanelId);
		}
	}

	public long getPid() {
		return _pid;
	}

	public void setPid(long pid) {
		_pid = pid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public String getActivePanelId() {
		if (_activePanelId == null) {
			return StringPool.BLANK;
		}
		else {
			return _activePanelId;
		}
	}

	public void setActivePanelId(String activePanelId) {
		_activePanelId = activePanelId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			Panel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Panel toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Panel)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	public Panel toUnescapedModel() {
		return (Panel)this;
	}

	@Override
	public Object clone() {
		PanelImpl panelImpl = new PanelImpl();

		panelImpl.setPid(getPid());
		panelImpl.setUserId(getUserId());
		panelImpl.setActivePanelId(getActivePanelId());

		panelImpl.resetOriginalValues();

		return panelImpl;
	}

	public int compareTo(Panel panel) {
		long primaryKey = panel.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Panel)) {
			return false;
		}

		Panel panel = (Panel)obj;

		long primaryKey = panel.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		PanelModelImpl panelModelImpl = this;

		panelModelImpl._originalUserId = panelModelImpl._userId;

		panelModelImpl._setOriginalUserId = false;

		panelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Panel> toCacheModel() {
		PanelCacheModel panelCacheModel = new PanelCacheModel();

		panelCacheModel.pid = getPid();

		panelCacheModel.userId = getUserId();

		panelCacheModel.activePanelId = getActivePanelId();

		String activePanelId = panelCacheModel.activePanelId;

		if ((activePanelId != null) && (activePanelId.length() == 0)) {
			panelCacheModel.activePanelId = null;
		}

		return panelCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{pid=");
		sb.append(getPid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", activePanelId=");
		sb.append(getActivePanelId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.Panel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>pid</column-name><column-value><![CDATA[");
		sb.append(getPid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>activePanelId</column-name><column-value><![CDATA[");
		sb.append(getActivePanelId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Panel.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] { Panel.class };
	private long _pid;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _activePanelId;
	private long _columnBitmask;
	private Panel _escapedModel;
}