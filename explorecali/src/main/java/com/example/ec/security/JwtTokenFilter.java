package com.example.ec.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by m.karandish on 5/30/2019.
 */
public class JwtTokenFilter extends GenericFilterBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
    private static final String BEARER = "Bearer";

    private JwtProvider jwtTokenProvider;

//    private ExploreCaliUserDetailsService userDetailsService;

//    public JwtTokenFilter(ExploreCaliUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    public JwtTokenFilter(JwtProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Determine if there is a JWT as part of the HTTP Request Header.
     * If it is valid then set the current context With the Authentication(user and roles) found in the token
     *
     * @param req Servlet Request
     * @param res Servlet Response
     * @param filterChain Filter Chain
     * @throws IOException
     * @throws ServletException
     */
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
//            throws IOException, ServletException {
//        LOGGER.info("Process request to check for a JSON Web Token ");
//        //Check for Authorization:Bearer JWT
//        String headerValue = ((HttpServletRequest)req).getHeader("Authorization");
//        getBearerToken(headerValue).ifPresent(token-> {
//            //Pull the Username and Roles from the JWT to construct the user details
//            userDetailsService.loadUserByJwtToken(token).isEnabled(userDetails -> {
//                //Add the user details (Permissions) to the Context for just this API invocation
//                SecurityContextHolder.getContext().setAuthentication(
//                        new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
//            });
//        });
//
//        //move on to the next filter in the chains
//        filterChain.doFilter(req, res);
//    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }
    /**
     * if present, extract the jwt token from the "Bearer <jwt>" header value.
     *
     * @param headerVal
     * @return jwt if present, empty otherwise
     */
    private Optional<String> getBearerToken(String headerVal) {
        if (headerVal != null && headerVal.startsWith(BEARER)) {
            return Optional.of(headerVal.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
}

