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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryUtil {

	public static <T> Indexer<T> getIndexer(Class<T> clazz) {
		return _indexerRegistry.getIndexer(clazz);
	}

	public static <T> Indexer<T> getIndexer(String className) {
		return _indexerRegistry.getIndexer(className);
	}

	public static List<IndexerPostProcessor> getIndexerPostProcessors(
		Indexer<?> indexer) {

		return _indexerRegistry.getIndexerPostProcessors(indexer);
	}

	public static List<IndexerPostProcessor> getIndexerPostProcessors(
		String className) {

		return _indexerRegistry.getIndexerPostProcessors(className);
	}

	public static Set<Indexer<?>> getIndexers() {
		return _indexerRegistry.getIndexers();
	}

	public static <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz) {
		return _indexerRegistry.nullSafeGetIndexer(clazz);
	}

	public static <T> Indexer<T> nullSafeGetIndexer(String className) {
		return _indexerRegistry.nullSafeGetIndexer(className);
	}

	private static volatile IndexerRegistry _indexerRegistry =
		ServiceProxyFactory.newServiceTrackedInstance(
			IndexerRegistry.class, IndexerRegistryUtil.class,
			"_indexerRegistry", false);

}