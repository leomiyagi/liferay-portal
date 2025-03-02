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

import {COOKIE_TYPES} from 'frontend-js-web';
import {useCallback, useEffect} from 'react';

import {useSessionState} from '../../../common/hooks/useSessionState';
import switchSidebarPanel from '../../actions/switchSidebarPanel';
import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../config/constants/highlightedCommentIdKey';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import getFragmentItem from '../../utils/getFragmentItem';

export default function useURLParser() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [, setHighlightedCommentId] = useSessionState(
		HIGHLIGHTED_COMMENT_ID_KEY,
		undefined,
		COOKIE_TYPES.NECESSARY
	);
	const layoutData = useSelector((state) => state.layoutData);
	const dispatch = useDispatch();
	const selectItem = useSelectItem();

	const selectFragment = useCallback(
		(messageId) => {
			const {fragmentEntryLinkId} = Object.values(
				fragmentEntryLinks
			).find((fragmentEntryLink) =>
				fragmentEntryLink.comments.find(
					(comment) =>
						comment.commentId === messageId ||
						comment.children.find(
							(childComment) =>
								childComment.commentId === messageId
						)
				)
			) || {fragmentEntryLinkId: null};

			const {itemId} =
				getFragmentItem(layoutData, fragmentEntryLinkId) || {};

			if (itemId) {
				selectItem(itemId);

				dispatch(
					switchSidebarPanel({
						sidebarOpen: true,
						sidebarPanelId: 'comments',
					})
				);
			}
		},
		[dispatch, fragmentEntryLinks, layoutData, selectItem]
	);

	useEffect(() => {
		const url = new URL(window.location.href);

		if (url.searchParams.has('messageId')) {
			setHighlightedCommentId(url.searchParams.get('messageId'));
			selectFragment(url.searchParams.get('messageId'));
			url.searchParams.delete('messageId');

			history.replaceState(null, document.head.title, url.href);
		}

		if (url.searchParams.has('itemId')) {
			selectItem(url.searchParams.get('itemId'));

			url.searchParams.delete('itemId');

			history.replaceState(null, document.head.title, url.href);
		}
	}, [selectFragment, setHighlightedCommentId, selectItem]);
}
