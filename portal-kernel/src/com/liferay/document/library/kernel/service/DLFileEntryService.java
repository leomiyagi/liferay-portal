/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.document.library.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;

import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service interface for DLFileEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFileEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryServiceUtil} to access the document library file entry remote service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public DLFileEntry addFileEntry(long groupId, long repositoryId,
		long folderId, String sourceFileName, String mimeType, String title,
		String description, String changeLog, long fileEntryTypeId,
		Map<String, DDMFormValues> ddmFormValuesMap, File file, InputStream is,
		long size, ServiceContext serviceContext) throws PortalException;

	public DLFileVersion cancelCheckOut(long fileEntryId)
		throws PortalException;

	/**
	* @deprecated As of Judson (7.1.x), replaced by {@link #checkInFileEntry(long, DLVersionNumberIncrease, String, ServiceContext)}
	*/
	@Deprecated
	public void checkInFileEntry(long fileEntryId, boolean major,
		String changeLog, ServiceContext serviceContext)
		throws PortalException;

	public void checkInFileEntry(long fileEntryId,
		DLVersionNumberIncrease dlVersionNumberIncrease, String changeLog,
		ServiceContext serviceContext) throws PortalException;

	public void checkInFileEntry(long fileEntryId, String lockUuid,
		ServiceContext serviceContext) throws PortalException;

	public DLFileEntry checkOutFileEntry(long fileEntryId,
		ServiceContext serviceContext) throws PortalException;

	public DLFileEntry checkOutFileEntry(long fileEntryId, String owner,
		long expirationTime, ServiceContext serviceContext)
		throws PortalException;

	public DLFileEntry copyFileEntry(long groupId, long repositoryId,
		long fileEntryId, long destFolderId, ServiceContext serviceContext)
		throws PortalException;

	public void deleteFileEntry(long fileEntryId) throws PortalException;

	public void deleteFileEntry(long groupId, long folderId, String title)
		throws PortalException;

	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchFileEntryByImageId(long imageId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long fileEntryId, String version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long fileEntryId, String version,
		boolean incrementCounter) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		int status, int start, int end, OrderByComparator<DLFileEntry> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		int start, int end, OrderByComparator<DLFileEntry> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		String[] mimeTypes, int status, int start, int end,
		OrderByComparator<DLFileEntry> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		String[] mimeTypes, int start, int end,
		OrderByComparator<DLFileEntry> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId,
		long fileEntryTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId,
		String[] mimeTypes);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId,
		String[] mimeTypes, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntry(long fileEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Lock getFileEntryLock(long fileEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersFileEntriesCount(long groupId, List<Long> folderIds,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		long rootFolderId, int start, int end,
		OrderByComparator<DLFileEntry> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		long repositoryId, long rootFolderId, String[] mimeTypes, int status,
		int start, int end, OrderByComparator<DLFileEntry> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		long rootFolderId, String[] mimeTypes, int status, int start, int end,
		OrderByComparator<DLFileEntry> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId,
		long repositoryId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId, String[] mimeTypes, int status)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFileEntryLock(long fileEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws PortalException;

	/**
	* @deprecated As of Judson (7.1.x), with no direct replacement
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isKeepFileVersionLabel(long fileEntryId,
		boolean majorVersion, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#isKeepFileVersionLabel(long, boolean, ServiceContext)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isKeepFileVersionLabel(long fileEntryId,
		ServiceContext serviceContext) throws PortalException;

	public DLFileEntry moveFileEntry(long fileEntryId, long newFolderId,
		ServiceContext serviceContext) throws PortalException;

	public Lock refreshFileEntryLock(String lockUuid, long companyId,
		long expirationTime) throws PortalException;

	public void revertFileEntry(long fileEntryId, String version,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long groupId, long creatorUserId, int status, int start,
		int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long groupId, long creatorUserId, long folderId,
		String[] mimeTypes, int status, int start, int end)
		throws PortalException;

	/**
	* @deprecated As of Judson (7.1.x), replaced by {@link #updateFileEntry(long, String, String, String, String, String, DLVersionNumberIncrease, long, Map, File, InputStream, long, ServiceContext)}
	*/
	@Deprecated
	public DLFileEntry updateFileEntry(long fileEntryId, String sourceFileName,
		String mimeType, String title, String description, String changeLog,
		boolean majorVersion, long fileEntryTypeId,
		Map<String, DDMFormValues> ddmFormValuesMap, File file, InputStream is,
		long size, ServiceContext serviceContext) throws PortalException;

	public DLFileEntry updateFileEntry(long fileEntryId, String sourceFileName,
		String mimeType, String title, String description, String changeLog,
		DLVersionNumberIncrease dlVersionNumberIncrease, long fileEntryTypeId,
		Map<String, DDMFormValues> ddmFormValuesMap, File file, InputStream is,
		long size, ServiceContext serviceContext) throws PortalException;

	public DLFileEntry updateStatus(long userId, long fileVersionId,
		int status, ServiceContext serviceContext,
		Map<String, Serializable> workflowContext) throws PortalException;

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException;

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException;
}