package com.bmw.seed.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class HttpClientConfig {
    //最大连接数
//    @Value("${http.maxTotal}")
//    private Integer maxTotal;
    private Integer maxTotal=300;

    //并发数
//    @Value("${http.defaultMaxPerRoute}")
//    private  Integer defaultMaxPerRoute;
    private  Integer defaultMaxPerRoute=50;

    //设置连接超时时间
//    @Value("${http.connectTimeout}")
//    private Integer connectTimeout;
    private Integer connectTimeout=1000;

    //设置连接请求最长时间
    //@Value("${http.connectionRequestTimeout}")
    //private Integer connectionRequestTimeout;
    private Integer connectionRequestTimeout=500;

    //数据传输最长时间
    //@Value("${http.socketTimeout}")
    //private Integer socketTimeout;
    private Integer socketTimeout=5000;

    //提交请求之前测试连接是否可用
//    @Value("${http.staleConnectionCheckEnabled}")
//       private boolean staleConnectionCheckEnabled;
        private boolean staleConnectionCheckEnabled=true;

    /**
     * 首先实例化一个连接池管理器，设置最大连接数、并发连接数
     *
     */

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager  getPoolingHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager httpClientConnectionManager=new PoolingHttpClientConnectionManager();
        //设置最大连接数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        //设置并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;

    }

    /**
     * 实例化连接池，设置连接池管理器。这里需要以参数形式注入上面实例化的连接器管理器
     *
     */

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager){

        /**HttpClientBuilder中的构造方法被protected修饰，所以这里不能使用new来实例化一个HttpClientBuilder，
         * 可以使用它的静态方法create()来获取HttpClientBuilder对象
         */
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }


    /**
     * 注入连接池，用于获取httpClient
     */
    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){

        return httpClientBuilder.build();

    }


    /**
     * Builder是RequestConfig的一个内部类  通过RequestConfig的custom方法来获取一个Builder对象
     * 设置builder的连接信息 这里可以设置proxy，cookieSpec等属性。可以在这里设置
     */
    @Bean("builder")
    public RequestConfig.Builder getBuilder(){
        RequestConfig.Builder builder=RequestConfig.custom();

        return builder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }

    /**
     * 使用builder构建一个RequestConfig对象
     */

    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder){
        return builder.build();
    }

}
