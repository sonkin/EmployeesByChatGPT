/*
 * Copyright (c) 2022 Luxoft Holding Inc.
 * All rights reserved.
 */

package com.luxoft.employees.cucumber;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Configure JUnit 5 test suite to run Cucumber tests.
 * To execute from the command line:
 * mvn -Dtest=UserStoryCucumberTest -DfailIfNoTests=false test
 */
@Suite
@SuiteDisplayName("Cucumber User Story Test Suite")
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
class CucumberTestSuite {
}
