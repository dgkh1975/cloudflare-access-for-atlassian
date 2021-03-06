package com.cloudflare.access.atlassian.confluence.auth;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudflare.access.atlassian.base.auth.FailedAuthenticationRequestHandler;

@Component
public class ConfluenceFailedAuthenticationRequestHandler implements FailedAuthenticationRequestHandler{

	private static final Logger log = LoggerFactory.getLogger(ConfluenceFailedAuthenticationRequestHandler.class);
	private RememberMeHelperService rememberMeService;

	@Inject
	public ConfluenceFailedAuthenticationRequestHandler(RememberMeHelperService rememberMeService) {
		this.rememberMeService = rememberMeService;
	}

	@Override
	public void handle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Throwable e) {
		try {
			rememberMeService.removeRememberMeCookie(httpRequest, httpResponse);
			sendErrorResponse(httpRequest, httpResponse, e);
		}catch (Throwable e2) {
			log.error("Unable to send 401 error with message " + e.getMessage(), e2);
			throw new RuntimeException("Unable to send response on auth failure", e2);
		}
	}

}
