package com.lcwd.electronicstore.security;

import com.lcwd.electronicstore.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        logger.info("Header : {}",requestHeader);
        String username=null;
        String token=null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e){
                logger.info("Illegal Argument Exception while fetching username ");
                e.printStackTrace();

            }catch (ExpiredJwtException e){
                logger.info("Given Jwt token is Expired");
                e.printStackTrace();

            } catch (MalformedJwtException e) {
                logger.info("Some Changes done in token ! Invalid token ");
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            logger.info("Invalid Header Value");
        }


        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            //fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken){
                //set Authentication
                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                logger.info("validation fails");
            }
        }
        filterChain.doFilter(request ,response);
    }
}
