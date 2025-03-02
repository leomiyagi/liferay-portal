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

package com.liferay.commerce.checkout.helper;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jan Brychta
 */
@ProviderType
public interface CommerceCheckoutStepHttpHelper {

	public String getOrderDetailURL(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException;

	public boolean isActiveBillingAddressCommerceCheckoutStep(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException;

	public boolean isActiveDeliveryTermCommerceCheckoutStep(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder,
			String languageId)
		throws PortalException;

	public boolean isActivePaymentMethodCommerceCheckoutStep(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException;

	public boolean isActivePaymentTermCommerceCheckoutStep(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
			String languageId)
		throws PortalException;

	public boolean isActiveShippingMethodCommerceCheckoutStep(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public boolean isCommercePaymentComplete(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException;

}