package com.cloud.kads.kadsagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * application configuration class
 * 
 * @author hgoel@244
 *
 */
@Component
@Configuration
@ComponentScan(basePackages = "org.eval.web.numberverifyapp")
public class KadsAgentApplicationConfig {

	@Value("${numberverifyapp.client.connector.connection.timeoutInMilliSeconds}")
	private int timeout;

//	@Bean
//	public CommonsRequestLoggingFilter logFilter() {
//		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//		filter.setIncludeQueryString(true);
//		filter.setIncludePayload(true);
//		filter.setMaxPayloadLength(10000);
//		filter.setIncludeHeaders(false);
//		filter.setAfterMessagePrefix("request: ");
//		return filter;
//	}
}
