package com.xquare.v1servicegit.common.config

import com.xquare.v1servicegit.BASE_PACKAGE
import com.xquare.v1servicegit.common.annotations.UseCase
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = [BASE_PACKAGE],
    includeFilters = [
        Filter(
            type = FilterType.ANNOTATION,
            classes = [
                UseCase::class
            ]
        )
    ]
)
class ComponentConfiguration
