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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelService;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.pricing.web.internal.constants.CommerceDiscountScreenNavigationConstants;
import com.liferay.commerce.pricing.web.internal.display.context.CommerceDiscountQualifiersDisplayContext;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceDiscountQualifiersScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceDiscount> {

	@Override
	public String getCategoryKey() {
		return CommerceDiscountScreenNavigationConstants.
			CATEGORY_KEY_QUALIFIERS;
	}

	@Override
	public String getEntryKey() {
		return CommerceDiscountScreenNavigationConstants.
			CATEGORY_KEY_QUALIFIERS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, "eligibility");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceDiscountScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_DISCOUNT_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CommerceDiscount commerceDiscount) {
		if (commerceDiscount == null) {
			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission = _commerceDiscountModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commerceDiscount.getCommerceDiscountId(), ActionKeys.UPDATE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceDiscountQualifiersDisplayContext
			commerceDiscountQualifiersDisplayContext =
				new CommerceDiscountQualifiersDisplayContext(
					_commerceChannelRelService, _commerceCurrencyLocalService,
					_commerceDiscountModelResourcePermission,
					_commerceDiscountAccountRelService,
					_commerceDiscountCommerceAccountGroupRelService,
					_commerceDiscountOrderTypeRelService,
					_commerceDiscountService, _commerceDiscountRuleService,
					_commerceDiscountRuleTypeRegistry,
					_commerceDiscountTargetRegistry, _percentageFormatter,
					httpServletRequest, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceDiscountQualifiersDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_discounts/qualifiers.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountQualifiersScreenNavigationCategory.class);

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscount)"
	)
	private ModelResourcePermission<CommerceDiscount>
		_commerceDiscountModelResourcePermission;

	@Reference
	private CommerceDiscountOrderTypeRelService
		_commerceDiscountOrderTypeRelService;

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CommerceDiscountRuleTypeRegistry _commerceDiscountRuleTypeRegistry;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommerceDiscountTargetRegistry _commerceDiscountTargetRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

	@Reference
	private PercentageFormatter _percentageFormatter;

	@Reference
	private Portal _portal;

}