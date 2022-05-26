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

export function getSpritemapPath(iconPack?: string): string {
	if (!Liferay.FeatureFlags['LPS-145112']) {
		return Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
	}

	if (!iconPack && Liferay.ThemeDisplay.isControlPanel()) {
		return getSystemSpritemapPath();
	}

	const packOrSite = iconPack ? 'pack' : 'site';
	const iconPackOrSiteId = iconPack || Liferay.ThemeDisplay.getSiteGroupId();

	return `${Liferay.Icons.basePath}/${packOrSite}/${iconPackOrSiteId}.svg`;
}

export function getSystemSpritemapPath(): string {
	if (!Liferay.FeatureFlags['LPS-145112']) {
		return Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
	}

	return Liferay.Icons.systemSpritemap;
}
