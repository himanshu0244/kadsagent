package com.cloud.kads.kadsagent.config;

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
@ComponentScan(basePackages = "com.cloud.kads.kadsagent")
public class KadsAgentApplicationConfig {

}
