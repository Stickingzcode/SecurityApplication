package com.example.SecurityApp.config;

import com.example.SecurityApp.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        Every request has a header and a body, To extract header from the request
//        and to extract the token from the header, do this
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userName;

        if(authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
//        the token starts from bearer then plus space... the index from bearer to the beginning of the token is
//        0 to 6, the token starts from index 7
        jwtToken = authorizationHeader.substring(7);
        userName = jwtService.extractUserName(jwtToken);

        if(userName != null && SecurityContextHolder.getContext() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            if(jwtService.isTokenValid(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request, response);

    }
}
