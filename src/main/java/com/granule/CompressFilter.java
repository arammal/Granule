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
package com.granule;

import com.granule.cache.TagCacheFactory;
import com.granule.logging.Logger;
import com.granule.logging.LoggerFactory;
import com.granule.settings.AbstractCompressorSettings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * User: Dario Wunsch
 * Date: 20.08.2010
 * Time: 4:16:28
 */
public class CompressFilter implements Filter {
	private final AbstractCompressorSettings compressorSettings;

	public CompressFilter(AbstractCompressorSettings compressorSettings) {
		this.compressorSettings = compressorSettings;
	}

	public CompressFilter() {
		this(null);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			TagCacheFactory.init(compressorSettings, filterConfig.getServletContext());
		} catch (IOException e) {
			throw new ServletException(e);
		}
		logger.info(this.getClass().getSimpleName() + " started");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getServletPath();
		boolean isRealPath = false;
		int pathLength = path.length();
		int slashPosition = path.lastIndexOf("/");
		if (pathLength > 0 && slashPosition > 0) isRealPath = true;
		if (!isRealPath && (new File(httpRequest.getSession().getServletContext().getRealPath(path)).exists()))
			isRealPath = true;
		if (isRealPath) {
			chain.doFilter(request, response);
			return;
		}
		if (pathLength > 0 && slashPosition >= 0) path = path.substring(slashPosition + 1);
		int dotPos = path.indexOf(".");
		if (pathLength > 0 && dotPos > 0) {
			String id = path.substring(0, dotPos);
			(new CompressorHandler()).handle(compressorSettings, httpRequest, (HttpServletResponse) response, id);
		}
	}

	public void destroy() {
	}

	private static final Logger logger = LoggerFactory.getLogger(CompressFilter.class);
}
