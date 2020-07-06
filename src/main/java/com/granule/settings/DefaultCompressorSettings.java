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
package com.granule.settings;

import com.granule.JSCompileException;
import com.granule.utils.Utf8Properties;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: Dario Wunsch Date: 22.06.2010 Time: 22:09:21
 */
public class DefaultCompressorSettings extends CompressorSettings {
	private String jsCompressMethod = CompressorSettingsHelper.JSFASTMIN_VALUE;
	private String cssCompressMethod = CompressorSettingsHelper.CSSFASTMIN_VALUE;
	private String cache = CompressorSettingsHelper.MEMORY_VALUE;
	private boolean checkTimestamps = true;
	private boolean ignoreMissedFiles = true;
	private boolean formatPrettyPrint = false;
	private boolean formatPrintInputDelimiter = false;
	private String locale = null;
	private String optimization = CompressorSettingsHelper.SIMPLE_OPTIMIZATIONS_VALUE;
	private boolean handleJavascript = true;
	private boolean handleCss = true;
	private boolean cleanJsDuplicates = true;
	private boolean cleanCssDuplicates = true;
	private List<String> closurePathes = new ArrayList<String>();
	private List<String> keepFirstCommentPathes = new ArrayList<String>();
	private String outputWrapper = null;
	private String cacheFileLocation = null;
	private String tagName = CompressorSettingsHelper.DEFAULT_TAG_NAME;
	private String contextRoot = null;
	private String basePath = null;


	public String getJsCompressMethod() {
		return jsCompressMethod;
	}

	public String getCache() {
		return cache;
	}

	public boolean isCheckTimestamps() {
		return checkTimestamps;
	}

	public boolean isIgnoreMissedFiles() {
		return ignoreMissedFiles;
	}

	public boolean isFormatPrettyPrint() {
		return formatPrettyPrint;
	}

	public boolean isFormatPrintInputDelimiter() {
		return formatPrintInputDelimiter;
	}

	public String getLocale() {
		return locale;
	}

	public String getOptimization() {
		return optimization;
	}

	public String getOutputWrapper() {
		return outputWrapper;
	}

	public String getOutputWrapperMarker() {
		return CompressorSettingsHelper.DEFAULT_OUTPUT_WRAPPER_MARKER;
	}

	public String getTagName() {
		return tagName;
	}

	public void load(Properties props) throws IOException {
		String readed;
		String mode = CompressorSettingsHelper.ALL_VALUE;

		if (props.containsKey(CompressorSettingsHelper.TAG_PROCESS_KEY)) {
			readed = props.getProperty(CompressorSettingsHelper.TAG_PROCESS_KEY);
			if (CompressorSettingsHelper.ALL_VALUE.equalsIgnoreCase(readed) || CompressorSettingsHelper.JAVASCRIPT_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.CSS_VALUE.equalsIgnoreCase(readed) || CompressorSettingsHelper.IGNORE_VALUE.equalsIgnoreCase(readed))
				mode = readed;
			if (CompressorSettingsHelper.ALL_VALUE.equalsIgnoreCase(mode)) {
				handleJavascript = true;
				handleCss = true;
			} else if (CompressorSettingsHelper.JAVASCRIPT_VALUE.equalsIgnoreCase(mode)) {
				handleJavascript = true;
				handleCss = false;
			} else if (CompressorSettingsHelper.CSS_VALUE.equalsIgnoreCase(mode)) {
				handleJavascript = false;
				handleCss = true;
			} else {
				handleJavascript = false;
				handleCss = false;
			}
		}

		if (props.containsKey(CompressorSettingsHelper.TAG_METHOD_CSS_KEY)) {
			readed = props.getProperty(CompressorSettingsHelper.TAG_METHOD_CSS_KEY);
			if (CompressorSettingsHelper.COMBINE_VALUE.equalsIgnoreCase(readed) || CompressorSettingsHelper.CSSFASTMIN_VALUE.equalsIgnoreCase(readed))
				cssCompressMethod = readed;
		}

		if (props.containsKey(CompressorSettingsHelper.COMPRESS_METHOD_TIMESTAMP_CHECK_KEY))
			checkTimestamps = CompressorSettingsHelper.getBoolean(props.getProperty(CompressorSettingsHelper.COMPRESS_METHOD_TIMESTAMP_CHECK_KEY), checkTimestamps);

		if (props.containsKey(CompressorSettingsHelper.JS_COMPRESS_METHOD_KEY)) {
			readed = props.getProperty(CompressorSettingsHelper.JS_COMPRESS_METHOD_KEY);
			if (CompressorSettingsHelper.COMBINE_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.CLOSURE_COMPILER_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.JSFASTMIN_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.AUTO_VALUE.equalsIgnoreCase(readed))
				jsCompressMethod = readed;
		}

		if (props.containsKey(CompressorSettingsHelper.CACHE_KEY)) {
			readed = props.getProperty(CompressorSettingsHelper.CACHE_KEY);
			if (CompressorSettingsHelper.NONE_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.MEMORY_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.DISK_CACHE_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.DISK_CACHE_VALUE_ADD2.equalsIgnoreCase(readed))
				if (CompressorSettingsHelper.DISK_CACHE_VALUE_ADD2.equalsIgnoreCase(readed))
					readed = CompressorSettingsHelper.DISK_CACHE_VALUE;
			cache = readed;
		}

		if (props.containsKey(CompressorSettingsHelper.CLOSURE_COMPILER_FORMATTING_PRETTY_PRINT_KEY))
			formatPrettyPrint = CompressorSettingsHelper.getBoolean(props.getProperty(CompressorSettingsHelper.CLOSURE_COMPILER_FORMATTING_PRETTY_PRINT_KEY),
					formatPrettyPrint);

		if (props.containsKey(CompressorSettingsHelper.CLOSURE_COMPILER_FORMATTING_PRINT_INPUT_DELIMITER_KEY))
			formatPrintInputDelimiter = CompressorSettingsHelper.getBoolean(props
					.getProperty(CompressorSettingsHelper.CLOSURE_COMPILER_FORMATTING_PRINT_INPUT_DELIMITER_KEY), formatPrintInputDelimiter);

		if (props.containsKey(CompressorSettingsHelper.CLOSURE_COMPILER_LOCALE_KEY))
			locale = props.getProperty(CompressorSettingsHelper.CLOSURE_COMPILER_LOCALE_KEY);

		if (props.containsKey(CompressorSettingsHelper.CLOSURE_COMPILER_COMPILATION_LEVEL_KEY)) {
			readed = props.getProperty(CompressorSettingsHelper.CLOSURE_COMPILER_COMPILATION_LEVEL_KEY);
			if (CompressorSettingsHelper.SIMPLE_OPTIMIZATIONS_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.WHITESPACE_ONLY_VALUE.equalsIgnoreCase(readed)
					|| CompressorSettingsHelper.ADVANCED_OPTIMIZATIONS_VALUE.equalsIgnoreCase(readed))
				optimization = readed;
		}

		if (props.containsKey(CompressorSettingsHelper.CLOSURE_COMPILER_OUTPUT_WRAPPER_KEY))
			outputWrapper = props.getProperty(CompressorSettingsHelper.CLOSURE_COMPILER_OUTPUT_WRAPPER_KEY);

		if (props.containsKey(CompressorSettingsHelper.TAG_CSS_CLEANDUPLICATES_KEY))
			cleanCssDuplicates = CompressorSettingsHelper.getBoolean(props.getProperty(CompressorSettingsHelper.TAG_CSS_CLEANDUPLICATES_KEY), cleanCssDuplicates);

		if (props.containsKey(CompressorSettingsHelper.TAG_JS_CLEANDUPICATES_KEY))
			cleanJsDuplicates = CompressorSettingsHelper.getBoolean(props.getProperty(CompressorSettingsHelper.TAG_JS_CLEANDUPICATES_KEY), cleanJsDuplicates);

		readFileListProperty(props, CompressorSettingsHelper.CLOSURE_ADD_PATH_KEY, closurePathes);

		readFileListProperty(props, CompressorSettingsHelper.KEEP_FIRST_COMMENT_PATH_KEY, keepFirstCommentPathes);

		if (props.containsKey(CompressorSettingsHelper.IGNORE_MISSED_FILES_KEY))
			ignoreMissedFiles = CompressorSettingsHelper.getBoolean(props.getProperty(CompressorSettingsHelper.IGNORE_MISSED_FILES_KEY), ignoreMissedFiles);

		if (props.containsKey(CompressorSettingsHelper.CACHE_FILE_LOCATION_KEY))
			cacheFileLocation = props.getProperty(CompressorSettingsHelper.CACHE_FILE_LOCATION_KEY);

		if (props.containsKey(CompressorSettingsHelper.TAG_NAME_KEY))
			tagName = props.getProperty(CompressorSettingsHelper.TAG_NAME_KEY);

		if (props.containsKey(CompressorSettingsHelper.CONTEXTROOT_KEY))
			contextRoot = props.getProperty(CompressorSettingsHelper.CONTEXTROOT_KEY);
	}

	private void readFileListProperty(Properties props, String settingName, List<String> list) throws IOException {
		if (props.containsKey(settingName)) {
			list.clear();
			String s = props.getProperty(settingName);
			StreamTokenizer st = new StreamTokenizer(new StringReader(s.replaceAll("\\\\", "\\\\\\\\")));
			st.resetSyntax();
			st.wordChars('\u0000', '\uFFFF');
			st.whitespaceChars(',', ',');
			st.quoteChar('\"');
			st.quoteChar('\'');
			st.eolIsSignificant(false);
			while (st.nextToken() != StreamTokenizer.TT_EOF)
				list.add(st.sval);
		}
	}

	public void load(InputStream in) throws IOException {
		BufferedInputStream is = new BufferedInputStream(in);
		Utf8Properties props = new Utf8Properties();
		props.load(is);
		load(props);
	}

	public DefaultCompressorSettings(InputStream in) throws IOException {
		load(in);
	}

	public DefaultCompressorSettings(Properties props) throws IOException {
		load(props);
	}

	public void setJsCompressMethod(String newMethod) {
		jsCompressMethod = newMethod;
		if (jsCompressMethod == null)
			jsCompressMethod = CompressorSettingsHelper.COMBINE_VALUE;
		else
			jsCompressMethod = jsCompressMethod.toLowerCase();
		if (!jsCompressMethod.equalsIgnoreCase(CompressorSettingsHelper.COMBINE_VALUE)
				&& !jsCompressMethod.equalsIgnoreCase(CompressorSettingsHelper.CLOSURE_COMPILER_VALUE)
				&& !jsCompressMethod.equalsIgnoreCase(CompressorSettingsHelper.JSFASTMIN_VALUE))
			jsCompressMethod = CompressorSettingsHelper.COMBINE_VALUE;
	}

	public boolean isHandleJavascript() {
		return handleJavascript;
	}

	public boolean isHandleCss() {
		return handleCss;
	}

	public String getCssCompressMethod() {
		return cssCompressMethod;
	}

	public boolean isCleanJsDuplicates() {
		return cleanJsDuplicates;
	}

	public boolean isCleanCssDuplicates() {
		return cleanCssDuplicates;
	}

	public List<String> getClosurePathes() {
		return closurePathes;
	}

	public List<String> getKeepFirstCommentPathes() {
		return keepFirstCommentPathes;
	}

	public String getCacheFileLocation() {
		return cacheFileLocation;
	}

	public String getContextRoot() {
		return contextRoot;
	}

	public String getBasePath() {
		return basePath;
	}


	public void setOptions(String options) throws JSCompileException {
		if (options != null) {
			try {
				ByteArrayInputStream is = new ByteArrayInputStream(options.getBytes("UTF-8"));
				load(is);
			} catch (IOException e) {
				throw new JSCompileException(e);
			}
		}
	}
}
