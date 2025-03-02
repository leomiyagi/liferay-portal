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

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayNavigationBar from '@clayui/navigation-bar';
import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {TFDSView} from './FDSViews';
import Details from './fds_view/Details';
import Pagination from './fds_view/Pagination';

const NAVIGATION_BAR_ITEMS = [
	{
		Component: Details,
		label: Liferay.Language.get('details'),
	},
	{
		Component: Pagination,
		label: Liferay.Language.get('pagination'),
	},
];

interface IFDSViewProps {
	fdsViewId: string;
	fdsViewsAPIURL: string;
	fdsViewsURL: string;
	namespace: string;
}

const FDSView = ({
	fdsViewId,
	fdsViewsAPIURL,
	fdsViewsURL,
	namespace,
}: IFDSViewProps) => {
	const [activeIndex, setActiveIndex] = useState(0);
	const [fdsView, setFDSView] = useState<TFDSView>();
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const getFDSView = async () => {
			const response = await fetch(`${fdsViewsAPIURL}/${fdsViewId}`, {
				headers: {
					Accept: 'application/json',
				},
			});

			const responseJSON = await response.json();

			if (responseJSON?.id) {
				setFDSView(responseJSON);

				setLoading(false);
			}
			else {
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});
			}
		};

		getFDSView();
	}, [fdsViewId, fdsViewsAPIURL]);

	const Content = NAVIGATION_BAR_ITEMS[activeIndex].Component;

	return (
		<>
			<ClayNavigationBar
				triggerLabel={NAVIGATION_BAR_ITEMS[activeIndex].label}
			>
				{NAVIGATION_BAR_ITEMS.map((item, index) => (
					<ClayNavigationBar.Item
						active={index === activeIndex}
						key={index}
					>
						<ClayButton onClick={() => setActiveIndex(index)}>
							{item.label}
						</ClayButton>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>

			{loading ? (
				<ClayLoadingIndicator />
			) : (
				fdsView && (
					<Content
						fdsView={fdsView}
						fdsViewsAPIURL={fdsViewsAPIURL}
						fdsViewsURL={fdsViewsURL}
						namespace={namespace}
					/>
				)
			)}
		</>
	);
};

export default FDSView;
