package dev.sassine.oauth2.scribe.config.security;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
@WebFilter("/*")
public class SecurityFilter implements Filter {
	
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String XSRF_TOKEN = "XSRF-TOKEN";
	private static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (GET.equalsIgnoreCase(httpServletRequest.getMethod())) {
            Cookie cookie = new Cookie(XSRF_TOKEN, UUID.randomUUID().toString());
            cookie.setHttpOnly(false);
            httpServletResponse.addCookie(cookie);
        } else if (POST.equalsIgnoreCase(httpServletRequest.getMethod())) {
            Cookie cookie = WebUtils.getCookie(httpServletRequest, XSRF_TOKEN);
            String csrfHeader = httpServletRequest.getHeader(X_XSRF_TOKEN);
            if (!Objects.equals(csrfHeader, cookie.getValue())) {
                httpServletResponse.setStatus(FORBIDDEN.value());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
