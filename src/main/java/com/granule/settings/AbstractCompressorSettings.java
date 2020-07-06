package com.granule.settings;

import com.granule.JSCompileException;

import java.util.ArrayList;
import java.util.List;

public class AbstractCompressorSettings {

	public String getJsCompressMethod() {
		return CompressorSettingsHelper.JSFASTMIN_VALUE;
	}

	public String getCache() {
		return CompressorSettingsHelper.MEMORY_VALUE;
	}

	public boolean isCheckTimestamps() {
		return true;
	}


	public boolean isIgnoreMissedFiles() {
		return true;
	}

	public boolean isFormatPrettyPrint() {
		return false;
	}

	public boolean isFormatPrintInputDelimiter() {
		return false;
	}

	public String getLocale() {
		return null;
	}

	public String getOptimization() {
		return CompressorSettingsHelper.SIMPLE_OPTIMIZATIONS_VALUE;
	}

	public String getOutputWrapper() {
		return null;
	}

	public String getOutputWrapperMarker() {
		return CompressorSettingsHelper.DEFAULT_OUTPUT_WRAPPER_MARKER;
	}

	public String getTagName() {
		return CompressorSettingsHelper.DEFAULT_TAG_NAME;
	}

	public boolean isHandleJavascript() {
		return true;
	}

	public boolean isHandleCss() {
		return true;
	}

	public String getCssCompressMethod() {
		return CompressorSettingsHelper.CSSFASTMIN_VALUE;
	}

	public boolean isCleanJsDuplicates() {
		return true;
	}

	public boolean isCleanCssDuplicates() {
		return true;
	}

	public List<String> getClosurePathes() {
		return new ArrayList<String>();
	}

	public List<String> getKeepFirstCommentPathes() {
		return new ArrayList<String>();
	}

	public String getCacheFileLocation() {
		return null;
	}

	public String getContextRoot() {
		return null;
	}

	public String getBasePath() {
		return null;
	}

	public void setOptions(String options) throws JSCompileException {

	}
}
