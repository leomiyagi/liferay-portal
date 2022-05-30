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

package com.liferay.client.extension.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ClientExtensionEntry service. Represents a row in the &quot;ClientExtensionEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.client.extension.model.impl.ClientExtensionEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.client.extension.model.impl.ClientExtensionEntryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntry
 * @generated
 */
@ProviderType
public interface ClientExtensionEntryModel
	extends BaseModel<ClientExtensionEntry>, ContainerModel, LocalizedModel,
			MVCCModel, ShardedModel, StagedAuditedModel, WorkflowedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a client extension entry model instance should use the {@link ClientExtensionEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this client extension entry.
	 *
	 * @return the primary key of this client extension entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this client extension entry.
	 *
	 * @param primaryKey the primary key of this client extension entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this client extension entry.
	 *
	 * @return the mvcc version of this client extension entry
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this client extension entry.
	 *
	 * @param mvccVersion the mvcc version of this client extension entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this client extension entry.
	 *
	 * @return the uuid of this client extension entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this client extension entry.
	 *
	 * @param uuid the uuid of this client extension entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the external reference code of this client extension entry.
	 *
	 * @return the external reference code of this client extension entry
	 */
	@AutoEscape
	public String getExternalReferenceCode();

	/**
	 * Sets the external reference code of this client extension entry.
	 *
	 * @param externalReferenceCode the external reference code of this client extension entry
	 */
	public void setExternalReferenceCode(String externalReferenceCode);

	/**
	 * Returns the client extension entry ID of this client extension entry.
	 *
	 * @return the client extension entry ID of this client extension entry
	 */
	public long getClientExtensionEntryId();

	/**
	 * Sets the client extension entry ID of this client extension entry.
	 *
	 * @param clientExtensionEntryId the client extension entry ID of this client extension entry
	 */
	public void setClientExtensionEntryId(long clientExtensionEntryId);

	/**
	 * Returns the company ID of this client extension entry.
	 *
	 * @return the company ID of this client extension entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this client extension entry.
	 *
	 * @param companyId the company ID of this client extension entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this client extension entry.
	 *
	 * @return the user ID of this client extension entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this client extension entry.
	 *
	 * @param userId the user ID of this client extension entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this client extension entry.
	 *
	 * @return the user uuid of this client extension entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this client extension entry.
	 *
	 * @param userUuid the user uuid of this client extension entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this client extension entry.
	 *
	 * @return the user name of this client extension entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this client extension entry.
	 *
	 * @param userName the user name of this client extension entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this client extension entry.
	 *
	 * @return the create date of this client extension entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this client extension entry.
	 *
	 * @param createDate the create date of this client extension entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this client extension entry.
	 *
	 * @return the modified date of this client extension entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this client extension entry.
	 *
	 * @param modifiedDate the modified date of this client extension entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the custom element cssur ls of this client extension entry.
	 *
	 * @return the custom element cssur ls of this client extension entry
	 */
	@AutoEscape
	public String getCustomElementCSSURLs();

	/**
	 * Sets the custom element cssur ls of this client extension entry.
	 *
	 * @param customElementCSSURLs the custom element cssur ls of this client extension entry
	 */
	public void setCustomElementCSSURLs(String customElementCSSURLs);

	/**
	 * Returns the custom element html element name of this client extension entry.
	 *
	 * @return the custom element html element name of this client extension entry
	 */
	@AutoEscape
	public String getCustomElementHTMLElementName();

	/**
	 * Sets the custom element html element name of this client extension entry.
	 *
	 * @param customElementHTMLElementName the custom element html element name of this client extension entry
	 */
	public void setCustomElementHTMLElementName(
		String customElementHTMLElementName);

	/**
	 * Returns the custom element ur ls of this client extension entry.
	 *
	 * @return the custom element ur ls of this client extension entry
	 */
	@AutoEscape
	public String getCustomElementURLs();

	/**
	 * Sets the custom element ur ls of this client extension entry.
	 *
	 * @param customElementURLs the custom element ur ls of this client extension entry
	 */
	public void setCustomElementURLs(String customElementURLs);

	/**
	 * Returns the custom element use esm of this client extension entry.
	 *
	 * @return the custom element use esm of this client extension entry
	 */
	public boolean getCustomElementUseESM();

	/**
	 * Returns <code>true</code> if this client extension entry is custom element use esm.
	 *
	 * @return <code>true</code> if this client extension entry is custom element use esm; <code>false</code> otherwise
	 */
	public boolean isCustomElementUseESM();

	/**
	 * Sets whether this client extension entry is custom element use esm.
	 *
	 * @param customElementUseESM the custom element use esm of this client extension entry
	 */
	public void setCustomElementUseESM(boolean customElementUseESM);

	/**
	 * Returns the description of this client extension entry.
	 *
	 * @return the description of this client extension entry
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this client extension entry.
	 *
	 * @param description the description of this client extension entry
	 */
	public void setDescription(String description);

	/**
	 * Returns the friendly url mapping of this client extension entry.
	 *
	 * @return the friendly url mapping of this client extension entry
	 */
	@AutoEscape
	public String getFriendlyURLMapping();

	/**
	 * Sets the friendly url mapping of this client extension entry.
	 *
	 * @param friendlyURLMapping the friendly url mapping of this client extension entry
	 */
	public void setFriendlyURLMapping(String friendlyURLMapping);

	/**
	 * Returns the i frame url of this client extension entry.
	 *
	 * @return the i frame url of this client extension entry
	 */
	@AutoEscape
	public String getIFrameURL();

	/**
	 * Sets the i frame url of this client extension entry.
	 *
	 * @param iFrameURL the i frame url of this client extension entry
	 */
	public void setIFrameURL(String iFrameURL);

	/**
	 * Returns the instanceable of this client extension entry.
	 *
	 * @return the instanceable of this client extension entry
	 */
	public boolean getInstanceable();

	/**
	 * Returns <code>true</code> if this client extension entry is instanceable.
	 *
	 * @return <code>true</code> if this client extension entry is instanceable; <code>false</code> otherwise
	 */
	public boolean isInstanceable();

	/**
	 * Sets whether this client extension entry is instanceable.
	 *
	 * @param instanceable the instanceable of this client extension entry
	 */
	public void setInstanceable(boolean instanceable);

	/**
	 * Returns the name of this client extension entry.
	 *
	 * @return the name of this client extension entry
	 */
	public String getName();

	/**
	 * Returns the localized name of this client extension entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this client extension entry
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this client extension entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this client extension entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this client extension entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this client extension entry
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this client extension entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this client extension entry
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this client extension entry.
	 *
	 * @return the locales and localized names of this client extension entry
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this client extension entry.
	 *
	 * @param name the name of this client extension entry
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this client extension entry in the language.
	 *
	 * @param name the localized name of this client extension entry
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this client extension entry in the language, and sets the default locale.
	 *
	 * @param name the localized name of this client extension entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this client extension entry from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this client extension entry
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this client extension entry from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this client extension entry
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the portlet category name of this client extension entry.
	 *
	 * @return the portlet category name of this client extension entry
	 */
	@AutoEscape
	public String getPortletCategoryName();

	/**
	 * Sets the portlet category name of this client extension entry.
	 *
	 * @param portletCategoryName the portlet category name of this client extension entry
	 */
	public void setPortletCategoryName(String portletCategoryName);

	/**
	 * Returns the properties of this client extension entry.
	 *
	 * @return the properties of this client extension entry
	 */
	@AutoEscape
	public String getProperties();

	/**
	 * Sets the properties of this client extension entry.
	 *
	 * @param properties the properties of this client extension entry
	 */
	public void setProperties(String properties);

	/**
	 * Returns the source code url of this client extension entry.
	 *
	 * @return the source code url of this client extension entry
	 */
	@AutoEscape
	public String getSourceCodeURL();

	/**
	 * Sets the source code url of this client extension entry.
	 *
	 * @param sourceCodeURL the source code url of this client extension entry
	 */
	public void setSourceCodeURL(String sourceCodeURL);

	/**
	 * Returns the type of this client extension entry.
	 *
	 * @return the type of this client extension entry
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this client extension entry.
	 *
	 * @param type the type of this client extension entry
	 */
	public void setType(String type);

	/**
	 * Returns the type settings of this client extension entry.
	 *
	 * @return the type settings of this client extension entry
	 */
	@AutoEscape
	public String getTypeSettings();

	/**
	 * Sets the type settings of this client extension entry.
	 *
	 * @param typeSettings the type settings of this client extension entry
	 */
	public void setTypeSettings(String typeSettings);

	/**
	 * Returns the status of this client extension entry.
	 *
	 * @return the status of this client extension entry
	 */
	@Override
	public int getStatus();

	/**
	 * Sets the status of this client extension entry.
	 *
	 * @param status the status of this client extension entry
	 */
	@Override
	public void setStatus(int status);

	/**
	 * Returns the status by user ID of this client extension entry.
	 *
	 * @return the status by user ID of this client extension entry
	 */
	@Override
	public long getStatusByUserId();

	/**
	 * Sets the status by user ID of this client extension entry.
	 *
	 * @param statusByUserId the status by user ID of this client extension entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId);

	/**
	 * Returns the status by user uuid of this client extension entry.
	 *
	 * @return the status by user uuid of this client extension entry
	 */
	@Override
	public String getStatusByUserUuid();

	/**
	 * Sets the status by user uuid of this client extension entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this client extension entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid);

	/**
	 * Returns the status by user name of this client extension entry.
	 *
	 * @return the status by user name of this client extension entry
	 */
	@AutoEscape
	@Override
	public String getStatusByUserName();

	/**
	 * Sets the status by user name of this client extension entry.
	 *
	 * @param statusByUserName the status by user name of this client extension entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName);

	/**
	 * Returns the status date of this client extension entry.
	 *
	 * @return the status date of this client extension entry
	 */
	@Override
	public Date getStatusDate();

	/**
	 * Sets the status date of this client extension entry.
	 *
	 * @param statusDate the status date of this client extension entry
	 */
	@Override
	public void setStatusDate(Date statusDate);

	/**
	 * Returns <code>true</code> if this client extension entry is approved.
	 *
	 * @return <code>true</code> if this client extension entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved();

	/**
	 * Returns <code>true</code> if this client extension entry is denied.
	 *
	 * @return <code>true</code> if this client extension entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied();

	/**
	 * Returns <code>true</code> if this client extension entry is a draft.
	 *
	 * @return <code>true</code> if this client extension entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft();

	/**
	 * Returns <code>true</code> if this client extension entry is expired.
	 *
	 * @return <code>true</code> if this client extension entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired();

	/**
	 * Returns <code>true</code> if this client extension entry is inactive.
	 *
	 * @return <code>true</code> if this client extension entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive();

	/**
	 * Returns <code>true</code> if this client extension entry is incomplete.
	 *
	 * @return <code>true</code> if this client extension entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete();

	/**
	 * Returns <code>true</code> if this client extension entry is pending.
	 *
	 * @return <code>true</code> if this client extension entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending();

	/**
	 * Returns <code>true</code> if this client extension entry is scheduled.
	 *
	 * @return <code>true</code> if this client extension entry is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled();

	/**
	 * Returns the container model ID of this client extension entry.
	 *
	 * @return the container model ID of this client extension entry
	 */
	@Override
	public long getContainerModelId();

	/**
	 * Sets the container model ID of this client extension entry.
	 *
	 * @param containerModelId the container model ID of this client extension entry
	 */
	@Override
	public void setContainerModelId(long containerModelId);

	/**
	 * Returns the container name of this client extension entry.
	 *
	 * @return the container name of this client extension entry
	 */
	@Override
	public String getContainerModelName();

	/**
	 * Returns the parent container model ID of this client extension entry.
	 *
	 * @return the parent container model ID of this client extension entry
	 */
	@Override
	public long getParentContainerModelId();

	/**
	 * Sets the parent container model ID of this client extension entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this client extension entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public ClientExtensionEntry cloneWithOriginalValues();

}