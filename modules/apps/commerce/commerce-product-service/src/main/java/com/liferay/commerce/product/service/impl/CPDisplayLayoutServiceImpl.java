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

package com.liferay.commerce.product.service.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.base.CPDisplayLayoutServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPDisplayLayout"
	},
	service = AopService.class
)
public class CPDisplayLayoutServiceImpl extends CPDisplayLayoutServiceBaseImpl {

	@Override
	public CPDisplayLayout addCPDisplayLayout(
			long groupId, Class<?> clazz, long classPK,
			String layoutPageTemplateEntryUuid, String layoutUuid)
		throws PortalException {

		_groupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_LAYOUT);

		_checkCPDisplayLayout(clazz.getName(), classPK, ActionKeys.VIEW);

		return cpDisplayLayoutLocalService.addCPDisplayLayout(
			getUserId(), groupId, clazz, classPK, layoutPageTemplateEntryUuid,
			layoutUuid);
	}

	@Override
	public void deleteCPDisplayLayout(long cpDisplayLayoutId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.getCPDisplayLayout(cpDisplayLayoutId);

		_groupPermission.check(
			getPermissionChecker(), cpDisplayLayout.getGroupId(),
			ActionKeys.ADD_LAYOUT);

		_checkCPDisplayLayout(
			cpDisplayLayout.getClassName(), cpDisplayLayout.getClassPK(),
			ActionKeys.UPDATE);

		cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
	}

	@Override
	public CPDisplayLayout fetchCPDisplayLayout(long cpDisplayLayoutId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.fetchCPDisplayLayout(cpDisplayLayoutId);

		if (cpDisplayLayout != null) {
			if (Validator.isNotNull(cpDisplayLayout.getLayoutUuid())) {
				LayoutPermissionUtil.check(
					getPermissionChecker(), _getLayout(cpDisplayLayout),
					ActionKeys.VIEW);
			}

			_checkCPDisplayLayout(
				cpDisplayLayout.getClassName(), cpDisplayLayout.getClassPK(),
				ActionKeys.VIEW);
		}

		return cpDisplayLayout;
	}

	@Override
	public BaseModelSearchResult<CPDisplayLayout> searchCPDisplayLayout(
			long companyId, long groupId, String className, Integer type,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		_groupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return cpDisplayLayoutLocalService.searchCPDisplayLayout(
			companyId, groupId, className, type, keywords, start, end, sort);
	}

	@Override
	public CPDisplayLayout updateCPDisplayLayout(
			long cpDisplayLayoutId, long classPK,
			String layoutPageTemplateEntryUuid, String layoutUuid)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.getCPDisplayLayout(cpDisplayLayoutId);

		if (Validator.isNotNull(layoutUuid)) {
			LayoutPermissionUtil.check(
				getPermissionChecker(), _getLayout(cpDisplayLayout),
				ActionKeys.VIEW);
		}

		_checkCPDisplayLayout(
			cpDisplayLayout.getClassName(), classPK, ActionKeys.VIEW);

		return cpDisplayLayoutLocalService.updateCPDisplayLayout(
			cpDisplayLayout.getCPDisplayLayoutId(), classPK,
			layoutPageTemplateEntryUuid, layoutUuid);
	}

	private void _checkCPDisplayLayout(
			String className, long classPK, String actionId)
		throws PortalException {

		if (className.equals(CPDefinition.class.getName())) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.fetchCPDefinition(classPK);

			if (cpDefinition == null) {
				throw new NoSuchCPDefinitionException();
			}

			CommerceCatalog commerceCatalog =
				_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
					cpDefinition.getGroupId());

			_commerceCatalogModelResourcePermission.check(
				getPermissionChecker(), commerceCatalog, actionId);
		}
		else if (className.equals(AssetCategory.class.getName())) {
			AssetCategoryPermission.check(
				getPermissionChecker(), classPK, actionId);
		}
	}

	private Layout _getLayout(CPDisplayLayout cpDisplayLayout) {
		Layout layout = _layoutLocalService.fetchLayout(
			cpDisplayLayout.getLayoutUuid(), cpDisplayLayout.getGroupId(),
			false);

		if (layout != null) {
			return layout;
		}

		return _layoutLocalService.fetchLayout(
			cpDisplayLayout.getLayoutUuid(), cpDisplayLayout.getGroupId(),
			true);
	}

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private LayoutLocalService _layoutLocalService;

}