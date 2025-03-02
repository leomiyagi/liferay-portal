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

package com.liferay.layout.admin.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = "form.navigator.entry.order:Integer=200",
	service = FormNavigatorEntry.class
)
public class LayoutSetLogoFormNavigatorEntry
	extends BaseLayoutSetFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_LAYOUT_SET_LOOK_AND_FEEL;
	}

	@Override
	public String getKey() {
		return "logo";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean isVisible(User user, LayoutSet layoutSet) {
		long companyId = layoutSet.getCompanyId();

		try {
			Company company = _companyLocalService.getCompany(companyId);

			if (!company.isSiteLogo()) {
				return false;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return true;
	}

	@Override
	protected String getJspPath() {
		return "/layout_set/logo.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetLogoFormNavigatorEntry.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)")
	private ServletContext _servletContext;

}