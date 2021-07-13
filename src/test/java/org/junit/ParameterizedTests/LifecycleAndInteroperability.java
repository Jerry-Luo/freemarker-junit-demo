package org.junit.ParameterizedTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/13 10:33
 */
public class LifecycleAndInteroperability {

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        // ...
    }

    @ParameterizedTest
    @ValueSource(strings = "apple")
    void testWithRegularParameterResolver(String argument, TestReporter testReporter) {
        testReporter.publishEntry("argument", argument);
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        // ...
    }
}
