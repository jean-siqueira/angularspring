package com.athena.helpdesk.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter{

    private final Log logger = LogFactory.getLog(this.getClass());
    
    public void init(FilterConfig fc)throws ServletException{
    	logger.info("Helpdesk-API | SimpleCORSFilter loaded");
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
                    throws IOException, ServletException {
        HttpServletResponse respose = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        respose.setHeader("Access-Control-Allow-Origin", "*");
        respose.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        respose.setHeader("Access-Control-Allow-Max-Age", "3600");
        respose.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization");
        
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        	respose.setStatus(HttpServletResponse.SC_OK);
        }else {
        	chain.doFilter(req, resp);
        }
        
    }
    
    @Override
    public void destroy() {
    	
    }

}
