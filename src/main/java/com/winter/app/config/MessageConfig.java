package com.winter.app.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer {
	
	//<bena class="">
	@Bean
	public LocaleResolver localeResolver() {
		//1. session
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.KOREAN);
		
		//2. Cookie
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.KOREAN);
		cookieLocaleResolver.setCookieName("lang");
		
		return resolver;
	}
	
	//Message Interceptor 객체 생성
	@Bean
	public LocaleChangeInterceptor changeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		
		interceptor.setParamName("lang");
		//parameter를 받아서 언어 구분
		//url?lang=en
		//url?lang=ko
		return interceptor;
	}
	
	
	
	
	
	
	
	
}
