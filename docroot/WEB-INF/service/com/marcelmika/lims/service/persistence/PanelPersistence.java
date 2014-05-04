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

package com.marcelmika.lims.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.marcelmika.lims.model.Panel;

/**
 * The persistence interface for the panel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PanelPersistenceImpl
 * @see PanelUtil
 * @generated
 */
public interface PanelPersistence extends BasePersistence<Panel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PanelUtil} to access the panel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the panel in the entity cache if it is enabled.
	*
	* @param panel the panel
	*/
	public void cacheResult(com.marcelmika.lims.model.Panel panel);

	/**
	* Caches the panels in the entity cache if it is enabled.
	*
	* @param panels the panels
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.model.Panel> panels);

	/**
	* Creates a new panel with the primary key. Does not add the panel to the database.
	*
	* @param pid the primary key for the new panel
	* @return the new panel
	*/
	public com.marcelmika.lims.model.Panel create(long pid);

	/**
	* Removes the panel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pid the primary key of the panel
	* @return the panel that was removed
	* @throws com.marcelmika.lims.NoSuchPanelException if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel remove(long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchPanelException;

	public com.marcelmika.lims.model.Panel updateImpl(
		com.marcelmika.lims.model.Panel panel, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the panel with the primary key or throws a {@link com.marcelmika.lims.NoSuchPanelException} if it could not be found.
	*
	* @param pid the primary key of the panel
	* @return the panel
	* @throws com.marcelmika.lims.NoSuchPanelException if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel findByPrimaryKey(long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchPanelException;

	/**
	* Returns the panel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pid the primary key of the panel
	* @return the panel, or <code>null</code> if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel fetchByPrimaryKey(long pid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the panel where userId = &#63; or throws a {@link com.marcelmika.lims.NoSuchPanelException} if it could not be found.
	*
	* @param userId the user ID
	* @return the matching panel
	* @throws com.marcelmika.lims.NoSuchPanelException if a matching panel could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel findByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchPanelException;

	/**
	* Returns the panel where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @return the matching panel, or <code>null</code> if a matching panel could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel fetchByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the panel where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching panel, or <code>null</code> if a matching panel could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel fetchByUserId(long userId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the panels.
	*
	* @return the panels
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Panel> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the panels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of panels
	* @param end the upper bound of the range of panels (not inclusive)
	* @return the range of panels
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Panel> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the panels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of panels
	* @param end the upper bound of the range of panels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of panels
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Panel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the panel where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @return the panel that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Panel removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchPanelException;

	/**
	* Removes all the panels from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of panels where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching panels
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of panels.
	*
	* @return the number of panels
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}