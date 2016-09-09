package com.wisely.apigateway;


import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.ContextLifecycleFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@EnableZuulProxy
@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    //CommandLineRunner接口很简单，知道spring boot的知道其功能，在工程启动后会执行对应run方法:
    @Component
    public static class MyCommandLineRunner implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            //MonitoringHelper.initMocks(); 启动监控
            MonitoringHelper.initMocks();

            initJavaFilters();
//            FilterLoader.getInstance().setCompiler(new GroovyCompiler());
//            try {
//                FilterFileManager.setFilenameFilter(new GroovyFileFilter());
//                FilterFileManager.init(10,"/Users/liaokailin/code/ieda/springcloud/myzuul/src/main/java/com/lkl/springcloud/zuul/filters/groovy/pre");
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        }
        ////initJavaFilters()方法中注册三种类型filter
        private void initJavaFilters() {
            final FilterRegistry r = FilterRegistry.instance();

            r.put("javaPreFilter", new ZuulFilter() {
                @Override
                public int filterOrder() {
                    return 50000;
                }

                @Override
                public String filterType() {
                    return "pre";
                }

                @Override
                public boolean shouldFilter() {
                    return true;
                }

                @Override
                public Object run() {
                    System.out.println("running javaPreFilter");
                    //RequestContext 在zuul有很重作用，在不同组件传递数据都是通过它来实现的
                    RequestContext.getCurrentContext().set("name", "test");
                    return null;
                }
            });

            r.put("javaRoutingFilter", new ZuulFilter() {
                @Override
                public int filterOrder() {
                    return 50000;
                }

                @Override
                public String filterType() {
                    return "route";
                }

                @Override
                public boolean shouldFilter() {
                    return true;
                }

                @Override
                public Object run() {
                    System.out.println("running javaRoutingFilter");
//                    try {
//                        RequestContext.getCurrentContext().getResponse().sendRedirect("http://blog.csdn.net/liaokailin/");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    return null;
                }
            });

            r.put("javaPostFilter", new ZuulFilter() {
                @Override
                public int filterOrder() {
                    return 50000;
                }

                @Override
                public String filterType() {
                    return "post";
                }

                @Override
                public boolean shouldFilter() {
                    return true;
                }

                @Override
                public Object run() {
                    System.out.println("running javaPostFilter");
                    System.out.println(RequestContext.getCurrentContext().get("name").toString());
                    return null;
                }

            });

        }

    }

    //通过ServletRegistrationBean构造ZuulServlet，该Servlet用以进行filter执行调度以及监控等等操作
    //访问 http://localhost:8080/test 进入该servlet
    @Bean
    public ServletRegistrationBean zuulServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new ZuulServlet());
        servlet.addUrlMappings("/test");
        return servlet;
    }

    //通过FilterRegistrationBean进行filter注册，ContextLifecycleFilter的核心功能是为了清除RequestContext；
    //请求上下文通过ThreadLocal存储，因此需要在请求完成后删除该对象。
    @Bean
    public FilterRegistrationBean contextLifecycleFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean(new ContextLifecycleFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }
}
