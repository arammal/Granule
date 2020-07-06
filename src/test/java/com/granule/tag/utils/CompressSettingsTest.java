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
package com.granule.tag.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.granule.settings.DefaultCompressorSettings;
import com.granule.settings.CompressorSettingsHelper;
import com.granule.JSCompileException;
import com.granule.utils.OptionsHandler;

import junit.framework.TestCase;

public class CompressSettingsTest extends TestCase {

	public void testAddPath() {
		DefaultCompressorSettings settings = getSettings(CompressorSettingsHelper.CLOSURE_ADD_PATH_KEY
				+ " = js/My Samples=,\"js\\test=1,benchmarks\",\"js/test=2,benchmarks\"");
		List<String> list = settings.getClosurePathes();
		assertEquals(list.get(0), "js/My Samples=");
		assertEquals(list.get(1), "js\\test=1,benchmarks");
		assertEquals(list.get(2), "js/test=2,benchmarks");
	}

	public void testOptionsAdding() {
		DefaultCompressorSettings settings = getSettings("");
		settings.setJsCompressMethod(CompressorSettingsHelper.CLOSURE_COMPILER_VALUE);
		try {
			String tagOptions= CompressorSettingsHelper.CLOSURE_ADD_PATH_KEY.replace(CompressorSettingsHelper.CLOSURE_COMPILER_VALUE+".", "") + "=\"js/My Samples2=\"";
			String preparedOptions = (new OptionsHandler()).handle(tagOptions, settings.getJsCompressMethod());
			settings.setOptions(preparedOptions);
		} catch (JSCompileException e) {
			// shouldn't happen as it in the memory
			throw new RuntimeException(e);
		}
		List<String> list = settings.getClosurePathes();
		assertEquals(list.get(0), "js/My Samples2=");
	}

	private DefaultCompressorSettings getSettings(String s) {
		try {
			DefaultCompressorSettings settings = new DefaultCompressorSettings(getInputStream(s));
			return settings;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private InputStream getInputStream(String settings) {
		try {
			return new ByteArrayInputStream(settings.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
