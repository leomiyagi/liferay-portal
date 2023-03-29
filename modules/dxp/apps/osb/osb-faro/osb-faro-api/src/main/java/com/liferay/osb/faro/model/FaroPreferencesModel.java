/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

/**
 * The base model interface for the FaroPreferences service. Represents a row in the &quot;OSBFaro_FaroPreferences&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.osb.faro.model.impl.FaroPreferencesImpl</code>.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroPreferences
 * @generated
 */
@ProviderType
public interface FaroPreferencesModel extends BaseModel<FaroPreferences> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a faro preferences model instance should use the {@link FaroPreferences} interface instead.
	 */

	/**
	 * Returns the primary key of this faro preferences.
	 *
	 * @return the primary key of this faro preferences
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this faro preferences.
	 *
	 * @param primaryKey the primary key of this faro preferences
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the faro preferences ID of this faro preferences.
	 *
	 * @return the faro preferences ID of this faro preferences
	 */
	public long getFaroPreferencesId();

	/**
	 * Sets the faro preferences ID of this faro preferences.
	 *
	 * @param faroPreferencesId the faro preferences ID of this faro preferences
	 */
	public void setFaroPreferencesId(long faroPreferencesId);

	/**
	 * Returns the group ID of this faro preferences.
	 *
	 * @return the group ID of this faro preferences
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this faro preferences.
	 *
	 * @param groupId the group ID of this faro preferences
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the user ID of this faro preferences.
	 *
	 * @return the user ID of this faro preferences
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this faro preferences.
	 *
	 * @param userId the user ID of this faro preferences
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this faro preferences.
	 *
	 * @return the user uuid of this faro preferences
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this faro preferences.
	 *
	 * @param userUuid the user uuid of this faro preferences
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this faro preferences.
	 *
	 * @return the user name of this faro preferences
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this faro preferences.
	 *
	 * @param userName the user name of this faro preferences
	 */
	public void setUserName(String userName);

	/**
	 * Returns the create time of this faro preferences.
	 *
	 * @return the create time of this faro preferences
	 */
	public long getCreateTime();

	/**
	 * Sets the create time of this faro preferences.
	 *
	 * @param createTime the create time of this faro preferences
	 */
	public void setCreateTime(long createTime);

	/**
	 * Returns the modified time of this faro preferences.
	 *
	 * @return the modified time of this faro preferences
	 */
	public long getModifiedTime();

	/**
	 * Sets the modified time of this faro preferences.
	 *
	 * @param modifiedTime the modified time of this faro preferences
	 */
	public void setModifiedTime(long modifiedTime);

	/**
	 * Returns the owner ID of this faro preferences.
	 *
	 * @return the owner ID of this faro preferences
	 */
	public long getOwnerId();

	/**
	 * Sets the owner ID of this faro preferences.
	 *
	 * @param ownerId the owner ID of this faro preferences
	 */
	public void setOwnerId(long ownerId);

	/**
	 * Returns the preferences of this faro preferences.
	 *
	 * @return the preferences of this faro preferences
	 */
	@AutoEscape
	public String getPreferences();

	/**
	 * Sets the preferences of this faro preferences.
	 *
	 * @param preferences the preferences of this faro preferences
	 */
	public void setPreferences(String preferences);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(FaroPreferences faroPreferences);

	@Override
	public int hashCode();

	@Override
	public CacheModel<FaroPreferences> toCacheModel();

	@Override
	public FaroPreferences toEscapedModel();

	@Override
	public FaroPreferences toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();

}