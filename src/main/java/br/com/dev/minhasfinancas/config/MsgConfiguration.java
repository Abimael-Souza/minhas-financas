package br.com.dev.minhasfinancas.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MsgConfiguration {
	
	@Bean
	public MessageSource validationsMessages() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("ISO-8859-1");
		
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean validationsMessagesFactory() {
		LocalValidatorFactoryBean beanValiationsMessages = new LocalValidatorFactoryBean();
		beanValiationsMessages.setValidationMessageSource(validationsMessages());
		
		return beanValiationsMessages;
	}

	public String getMessage(String key) {
		return validationsMessages().getMessage(key, null, Locale.getDefault());
	}
}