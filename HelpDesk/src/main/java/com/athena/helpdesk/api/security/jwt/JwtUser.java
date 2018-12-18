package com.athena.helpdesk.api.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails{

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String userName;
    private final String password;
    
    
}
