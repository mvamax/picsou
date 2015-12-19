package io.picsou.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
        implements EmbeddedServletContainerCustomizer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Resources without Spring Security. No cache control response headers.
 
    		registry.addResourceHandler("/webjars/**").addResourceLocations(
    				"classpath:/META-INF/resources/webjars/");
    		 registry.addResourceHandler("/static/**")
             .addResourceLocations("classpath:/static/");
        // Resources controlled by Spring Security, which
        // adds "Cache-Control: must-revalidate".
//        registry.addResourceHandler("/static/**")
//            .addResourceLocations("classpath:/static/")
//            .setCachePeriod(3600*24);
    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer arg0) {
		// TODO Auto-generated method stub
		
	}
}