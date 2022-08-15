package com.test.orderbook.restservice;

import org.springframework.security.core.Transient;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

@Transient
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String principalRequestHeader;

    public APIKeyAuthFilter(String principalRequestHeader) {
        this.principalRequestHeader = principalRequestHeader;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(principalRequestHeader);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
