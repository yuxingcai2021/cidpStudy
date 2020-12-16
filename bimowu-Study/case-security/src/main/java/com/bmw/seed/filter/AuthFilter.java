package com.bmw.seed.filter;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *使用filter进行请求拦截
 */
@Slf4j
//@WebFilter(filterName = "authFilter",urlPatterns = "/*")
public class AuthFilter implements Filter {

    //此处设置拦截所有，然后根据配置文件只拦截部分url，也可以直接在上面的urlPatterns中拦截全部url
    @Value("${auth.login.pattern}")
    private String urlPattern;
    //要跳转的MockUrl,本例中是在一个项目中，实际环境登录中心和业务中心一般是两个不同项目，子域名都不一样
    @Value("${auth.login.loginUrl}")
     private String loginUrl;
    //本项目对应的前缀域名
    @Value("${auth.login.host}")
    private String host;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest= (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse= (HttpServletResponse) servletResponse;
        String url = httpRequest.getRequestURI();

        log.info("====url:"+url);
        if (url.matches(urlPattern)){
            //需要拦截
            String user = (String) httpRequest.getSession().getAttribute("user");
            if(user!=null){
                //不需要拦截，放行，因为已经登录过了
                filterChain.doFilter(httpRequest,httpResponse);
                return;
            }else{
                //生成要跳转回来的url，   获取被拦截的url？
                String requestUrl = getRequestUrl(httpRequest);
                //在本例中是在一个项目中，可以用forward；实际环境登陆中心和业务中心一般是两个不同项目，子域名都不一样，因此实际环境一般用redirect跳转方式
                httpResponse.sendRedirect(loginUrl+"?url="+requestUrl);
                return;
            }
        }
        filterChain.doFilter(httpRequest,httpResponse);
        return;

    }
    private String getRequestUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        //实际的host在跳转项目中都包含域名，也可能多个域名对应一个服务，为了集群方便，域名一般从配置文件中获取
        String requestURL = host + request.getRequestURI();//项目前缀域名+请求
        /**
         request.getQueryString()获得请求参数字符串
         例如：
         http://localhost/test.do?a=b&c=d&e=f
         通过request.getQueryString()得到的是
         a=b&c=d&e=f；
         */
        String queryString = request.getQueryString();
        if (StringUtils.isEmpty(queryString)) {//没有请求参数直接返回请求
            return requestURL;
        } else {
            StringBuilder result = new StringBuilder();
            result.append(URLEncoder.encode(requestURL + "?", "UTF-8"));//给请求加上请参数
            String[] qsArray = queryString.split("&");//分割字符串，获取每个请求参数对 如"a=b "
            String[] qsPair = null;
            if (qsArray != null && qsArray.length > 0) {
                for (String qsKeyValue : qsArray) {
                    qsPair = qsKeyValue.split("=");//获取每个请求参数
                    if (qsPair != null && qsPair.length == 2) {//参数拼接，如"a=b&"
                        result.append(qsPair[0]).append(URLEncoder.encode("=", "UTF-8"));
                        result.append(URLEncoder.encode(qsPair[1], "UTF-8"));
                        result.append(URLEncoder.encode("&", "UTF-8"));
                    }
                }
            }
            return result.toString();
        }

    }
}
