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

package com.liferay.account.admin.web.internal.application.list;

import com.liferay.account.constants.AccountPanelCategoryKeys;
import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL,
		"panel.category.order:Integer=200"
	},
	service = PanelCategory.class
)
public class AccountEntriesAdminPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return AccountPanelCategoryKeys.CONTROL_PANEL_ACCOUNT_ENTRIES_ADMIN;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "accounts");
	}

	@Reference
	private Language _language;

}