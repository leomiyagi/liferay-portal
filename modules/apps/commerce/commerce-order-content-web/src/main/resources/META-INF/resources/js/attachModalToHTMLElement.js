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

import {openModal} from 'frontend-js-web';

export default function ({HTMLElementId, modalContent, modalTitle}) {
	document
		.getElementById(HTMLElementId)
		.addEventListener('click', (event) => {
			event.preventDefault();

			openModal({
				bodyHTML: modalContent,
				containerProps: {
					center: true,
				},
				size: 'lg',
				title: modalTitle,
			});
		});
}
