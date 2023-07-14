package com.xquare.v1servicegit.common.config

import com.xquare.v1servicegit.BASE_PACKAGE
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(basePackages = [BASE_PACKAGE])
@Configuration
class ConfigurationPropertyConfiguration
