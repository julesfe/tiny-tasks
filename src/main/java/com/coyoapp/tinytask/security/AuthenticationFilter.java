package com.coyoapp.tinytask.security;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.coyoapp.tinytask.service.UserDetailsImpl;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  public static final String HEADER_FIELD_USERNAME = "X-Username";

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

  private static final String FALLBACK_USER = "UNKNOWN USER";

  @Override
  protected void doFilterInternal(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final FilterChain filterChain)
    throws ServletException, IOException {
    final Optional<String> token = ofNullable(request.getHeader(HEADER_FIELD_USERNAME));

    if (!token.isPresent()) {
      LOG.debug(
        "missing token X-Username for request {}; set user details to '{}'",
        request.getRequestURI(),
        FALLBACK_USER);
    }

    setUserDetailsForSecurityContext(request, token.orElse(FALLBACK_USER));
    filterChain.doFilter(request, response);
  }

  private void setUserDetailsForSecurityContext(
    final HttpServletRequest request, final String token) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(new UserDetailsImpl(token), null, emptyList());

    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }
}
