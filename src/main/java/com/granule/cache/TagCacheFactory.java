/*
 * Copyright 2010 Granule Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.granule.cache;

import com.granule.JSCompileException;
import com.granule.settings.CompressorSettings;
import com.granule.settings.DefaultCompressorSettings;
import com.granule.settings.CompressorSettingsHelper;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;

/**
 * User: Dario Wunsch
 * Date: 23.07.2010
 * Time: 3:34:11
 */
public class TagCacheFactory {
	private static TagCacheImpl instance = null;

	public static TagCache getInstance() throws JSCompileException {
		if (instance == null)
			throw new JSCompileException("Granule cache was loaded not on the server start-up. Recommended to add the parameter <load-on-startup>1<load-on-startup> into servlet configuration.");
		return instance;
	}

	public static void init(CompressorSettings settings, ServletContext context) throws IOException {
		if (settings == null) {
			settings = initInstance(context.getRealPath("/"));
		}

		instance.initWeb(context, settings);
	}

	private static CompressorSettings initInstance(String rootPath) throws IOException {
		CompressorSettings settings = getCompressorSettings(rootPath);
		if (settings.getCache().equals(CompressorSettingsHelper.DISK_CACHE_VALUE))
			instance = FileCache.getInstance();
		else instance = MemoryCache.getInstance();
		return settings;
	}

	public static void init(String rootPath) throws IOException {
		initInstance(rootPath);
		instance.initForStandalone(rootPath, getCompressorSettings(rootPath));
	}

	protected static final Properties properties = new Properties();

	protected static boolean propertiesLoaded = false;

	public static CompressorSettings getCompressorSettings(String rootPath) throws IOException {
		synchronized (properties) {
			if (!propertiesLoaded) {
				loadSettings(null);
			}
		}
		return new DefaultCompressorSettings(properties);
	}

	private static void loadSettings(HashMap<String, String> addition) {
		ResourceBundle resource = ResourceBundle.getBundle("granule", Locale.getDefault());
		if (resource != null) {
			properties.putAll(toProperties(resource));
		}

		if (addition != null) {
			properties.putAll(addition);
		}

		propertiesLoaded = true;
	}

	private static Properties toProperties(ResourceBundle resource) {
		Properties properties = new Properties();
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}
		return properties;
	}

}
