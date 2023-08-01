package com.jwttoken31.securityJWTTokenValidation.security;

import com.jwttoken31.securityJWTTokenValidation.constants.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.util.Objects;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER= LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("sadasdsadasdasdsadasda");
        //We are fetching the token from the request Header which is stored as "Authorization"
        String requestHeader= request.getHeader(SecurityConstants.JWT_HEADER);
        LOGGER.info("Header : {}", requestHeader);

        String userName = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer")){

            // If the request header contains the Bearer+Token here we are removing the Bearer from requestHeader and keeping the Token for Further validation
            token = requestHeader.substring(7);
            try{
                userName= this.jwtHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                LOGGER.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                LOGGER.info("Given jwt token is expired");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                LOGGER.info("Some changes has been done in token !! Invalid Token");
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            LOGGER.info("Invalid Header Value !!");
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){

            //fetch user detail from username
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(userName);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if(validateToken){

                //Set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else {
                LOGGER.info("JWT Token Validation Fails !! Please Retry Login again with valid Credentials");
            }

        }
        filterChain.doFilter(request,response);

    }
}
