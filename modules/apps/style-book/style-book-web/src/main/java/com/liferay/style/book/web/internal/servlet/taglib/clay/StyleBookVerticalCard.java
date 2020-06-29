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

package com.liferay.style.book.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;
import com.liferay.style.book.web.internal.servlet.taglib.util.StyleBookEntryActionDropdownItemsProvider;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public StyleBookVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_styleBookEntry = (StyleBookEntry)baseModel;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		StyleBookEntryActionDropdownItemsProvider
			styleBookEntryActionDropdownItemsProvider =
				new StyleBookEntryActionDropdownItemsProvider(
					_styleBookEntry, _renderRequest, _renderResponse);

		return styleBookEntryActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getIcon() {
		return "magic";
	}

	@Override
	public String getTitle() {
		return _styleBookEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public String getDefaultEventHandler() {
		return StyleBookWebKeys.STYLE_BOOK_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StyleBookEntry _styleBookEntry;

}