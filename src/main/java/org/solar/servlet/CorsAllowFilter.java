package org.solar.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  跨域过滤器
 * @author
 * @version
 * @since 2016年6月19日
 */
public class CorsAllowFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,token");

        String uri=request.getRequestURI();
            chain.doFilter(req,res);

    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}


}
