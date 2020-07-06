package com.granule.settings;

public class CompressorSettingsHelper {
	public static final String TAG_PROCESS_KEY = "tag.process";
	public static final String TAG_NAME_KEY = "tag.name";
	public static final String JS_COMPRESS_METHOD_KEY = "tag.method.javascript";
	public static final String TAG_METHOD_CSS_KEY = "tag.method.css";
	public static final String TAG_JS_CLEANDUPICATES_KEY = "tag.js.cleandupicates";
	public static final String TAG_CSS_CLEANDUPLICATES_KEY = "tag.css.cleanduplicates";
	public static final String CLOSURE_COMPILER_LOCALE_KEY = "closure-compiler.locale";
	public static final String CLOSURE_ADD_PATH_KEY = "closure-compiler.add-path";
	public static final String KEEP_FIRST_COMMENT_PATH_KEY = "keepfirstcommentpath";
	public static final String IGNORE_MISSED_FILES_KEY = "ignorenotfoundfiles";
	public static final String CACHE_FILE_LOCATION_KEY = "cache.file.location";
	public static final String CLOSURE_COMPILER_COMPILATION_LEVEL_KEY = "closure-compiler.compilation_level";
	public static final String CLOSURE_COMPILER_FORMATTING_PRETTY_PRINT_KEY = "closure-compiler.formatting.pretty_print";
	public static final String CLOSURE_COMPILER_FORMATTING_PRINT_INPUT_DELIMITER_KEY = "closure-compiler.formatting.print_input_delimiter";
	public static final String CLOSURE_COMPILER_OUTPUT_WRAPPER_KEY = "closure-compiler.output_wrapper";
	public static final String COMPRESS_METHOD_TIMESTAMP_CHECK_KEY = "timestampcheck";

	public static final String NONE_VALUE = "none";
	public static final String CLOSURE_COMPILER_VALUE = "closure-compiler";
	public static final String JSFASTMIN_VALUE = "jsfastmin";
	public static final String AUTO_VALUE = "auto";
	public static final String COMBINE_VALUE = "combine";
	public static final String CSSFASTMIN_VALUE = "cssfastmin";
	public static final String CACHE_KEY = "cache";
	public static final String MEMORY_VALUE = "memory";
	public static final String DISK_CACHE_VALUE = "disk";
	public static final String DISK_CACHE_VALUE_ADD2 = "file";
	public static final String IGNORE_VALUE = "ignore";
	public static final String ALL_VALUE = "all";
	public static final String JAVASCRIPT_VALUE = "javascript";
	public static final String CSS_VALUE = "css";
	public static final String SIMPLE_OPTIMIZATIONS_VALUE = "SIMPLE_OPTIMIZATIONS";
	public static final String WHITESPACE_ONLY_VALUE = "WHITESPACE_ONLY";
	public static final String ADVANCED_OPTIMIZATIONS_VALUE = "ADVANCED_OPTIMIZATIONS";
	public static final String DEFAULT_TAG_NAME = "g:compress";
	public static final String CONTEXTROOT_KEY = "contextroot";
	public static final String DEFAULT_OUTPUT_WRAPPER_MARKER = "%output%";

	public static boolean getBoolean(String str, boolean defaultvalue) {
		boolean result = "yes".equalsIgnoreCase(str)
				|| "on".equalsIgnoreCase(str)
				|| "true".equalsIgnoreCase(str)
				|| "1".equalsIgnoreCase(str);
		if (result)
			return true;
		result = "no".equalsIgnoreCase(str)
				|| "off".equalsIgnoreCase(str)
				|| "false".equalsIgnoreCase(str)
				|| "0".equalsIgnoreCase(str);
		if (result)
			return false;
		return defaultvalue;
	}
}
