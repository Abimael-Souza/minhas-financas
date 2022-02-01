package br.com.dev.minhasfinancas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSource {

	public class AppConfig {

	    @Bean(name="messageSource1")
	    public ResourceBundleMessageSource getMessageSource() throws Exception{

	    	ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
	    	resourceBundleMessageSource.setBasenames("messages");
	    	resourceBundleMessageSource.setDefaultEncoding("UTF-8");
	    	
	        return resourceBundleMessageSource;
	    }
	}
}
