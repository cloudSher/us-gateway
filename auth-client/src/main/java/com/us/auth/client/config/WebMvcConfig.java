package com.us.auth.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
@PropertySource("classpath:sparklr.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
		ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
		ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
		contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
		contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
		contentViewResolver.setDefaultViews(Arrays.<View> asList(new MappingJackson2JsonView()));
		return contentViewResolver;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}



	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new BufferedImageHttpMessageConverter());
	}

	@Configuration
	@EnableOAuth2Client
	protected static class ResourceConfiguration {

		@Value("${accessTokenUri}")
		private String accessTokenUri;

		@Value("${userAuthorizationUri}")
		private String userAuthorizationUri;

		@Bean
		public OAuth2ProtectedResourceDetails sparklr() {
			AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
			details.setId("sparklr/tonr");
			details.setClientId("tonr");
			details.setClientSecret("secret");
			details.setAccessTokenUri(accessTokenUri);
			details.setUserAuthorizationUri(userAuthorizationUri);
			details.setScope(Arrays.asList("read", "write"));
			return details;
		}

		@Bean
		public OAuth2ProtectedResourceDetails sparklrRedirect() {
			AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
			details.setId("sparklr/tonr-redirect");
			details.setClientId("tonr-with-redirect");
			details.setClientSecret("secret");
			details.setAccessTokenUri(accessTokenUri);
			details.setUserAuthorizationUri(userAuthorizationUri);
			details.setScope(Arrays.asList("read", "write"));
			details.setUseCurrentUri(false);
			return details;
		}

		@Bean
		public OAuth2ProtectedResourceDetails facebook() {
			AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
			details.setId("facebook");
			details.setClientId("233668646673605");
			details.setClientSecret("33b17e044ee6a4fa383f46ec6e28ea1d");
			details.setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
			details.setUserAuthorizationUri("https://www.facebook.com/dialog/oauth");
			details.setTokenName("oauth_token");
			details.setAuthenticationScheme(AuthenticationScheme.query);
			details.setClientAuthenticationScheme(AuthenticationScheme.form);
			return details;
		}

		@Bean
		public OAuth2ProtectedResourceDetails trusted() {
			ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
			details.setId("sparklr/trusted");
			details.setClientId("my-client-with-registered-redirect");
			details.setAccessTokenUri(accessTokenUri);
			details.setScope(Arrays.asList("trust"));
			return details;
		}

		@Bean
		public OAuth2RestTemplate facebookRestTemplate(OAuth2ClientContext clientContext) {
			OAuth2RestTemplate template = new OAuth2RestTemplate(facebook(), clientContext);
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
					MediaType.valueOf("text/javascript")));
			template.setMessageConverters(Arrays.<HttpMessageConverter<?>> asList(converter));
			return template;
		}

//		@Bean
//		public OAuth2RestTemplate sparklrRestTemplate(OAuth2ClientContext clientContext) {
//			return new OAuth2RestTemplate(sparklr(), clientContext);
//		}
//
//		@Bean
//		public OAuth2RestTemplate sparklrRedirectRestTemplate(OAuth2ClientContext clientContext) {
//			return new OAuth2RestTemplate(sparklrRedirect(), clientContext);
//		}

		@Bean
		public OAuth2RestTemplate trustedClientRestTemplate() {
			return new OAuth2RestTemplate(trusted(), new DefaultOAuth2ClientContext());
		}

	}

}
