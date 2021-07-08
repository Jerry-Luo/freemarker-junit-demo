package org.junit.TestInstanceLifecycle;

import org.junit.jupiter.api.TestInstance;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/8 22:51
 */

/**
 * If a test class or test interface is not annotated with @TestInstance, JUnit Jupiter will use a default lifecycle mode.
 * The standard default mode is PER_METHOD;
 *
 * For example, to set the default test instance lifecycle mode to Lifecycle.PER_CLASS, you can start your JVM with the following system property.
 * -Djunit.jupiter.testinstance.lifecycle.default=per_class
 *
 * Note, however, that setting the default test instance lifecycle mode via the JUnit Platform configuration file is a more robust solution since the configuration file can be checked into a version control system along with your project and can therefore be used within IDEs and your build software.
 */
@TestInstance(org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS)
public class TestInstanceDemo {
}
