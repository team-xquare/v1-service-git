package com.xquare.git

import io.kotest.core.spec.style.DescribeSpec

abstract class BaseApplicationTest(testcase: DescribeSpec.() -> Unit) : DescribeSpec(testcase)
