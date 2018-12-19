package com.athena.helpdesk.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter{

    private final Log logger = LogFactory.getLog(this.getClass());
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
        // TODO Auto-generated method stub
        
    }

}
