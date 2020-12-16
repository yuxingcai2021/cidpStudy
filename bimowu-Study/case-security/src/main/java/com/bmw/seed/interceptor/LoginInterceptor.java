package com.bmw.seed.interceptor;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 使用interceptor拦截器进行请求拦截
 */
@Slf4j
@Component//声明为组件
public class LoginInterceptor implements HandlerInterceptor {
    //此处设置的是拦截所有，然后根据配置文件只拦截部分url
    @Value("${auth.login.pattern}")
    private  String  urlPattern;
    //被拦截后跳转的URL,本例中是一个项目，实际环境的登录中心和业务中心一般是两个不同项目，子域名都不一样
    @Value("${auth.login.loginUrl}")
    private String  loginUrl;
    //项目对应的前缀名
    @Value("${auth.login.host}")
    private String host;

    /**
     * 在请求处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest httpRequest=request;
        HttpServletResponse httpResponse=response;
        String url=request.getRequestURI();

        log.info("====url:"+url);
        if(url.matches(urlPattern)){
            //需要拦截
            String user = (String) request.getSession().getAttribute("user");
            if(user!=null){
                //不需要拦截，登录过了，直接放行
                return true;
            }else{
                //生成跳转回来的url
                String requestUrl = getRequestUrl(request);
                //本例是一个项目中，可以用forward；实际环境登录中心和业务中心是两个不同的项目，子域名都不一样，实际环境一般用redirect
                response.sendRedirect(loginUrl+"?url="+requestUrl);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        //请求处理之后调用，在视图渲染之前调用
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //整个请求结束，也就是在DispatchServlet 渲染视图之后（主要对资源进行清理工作）
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
