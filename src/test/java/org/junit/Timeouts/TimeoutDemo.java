package org.junit.Timeouts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/13 17:51
 */
@Timeout(3)
public class TimeoutDemo {

    @BeforeEach
    @Timeout(5)
    void setUp() {
        // fails if execution time exceeds 5 seconds
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void failsIfExecutionTimeExceeds100Milliseconds() {
        // fails if execution time exceeds 100 milliseconds
    }

    /**
     * If you need more control over polling intervals and greater flexibility with asynchronous tests,
     * consider using a dedicated library such as Awaitility.
     *
     * JUnit Jupiter supports the junit.jupiter.execution.timeout.mode configuration parameter to configure when timeouts are applied.
     * There are three modes: enabled, disabled, and disabled_on_debug. The default mode is enabled.
     * A VM runtime is considered to run in debug mode when one of its input parameters starts with -agentlib:jdwp.
     * This heuristic is queried by the disabled_on_debug mode.
     */
    @Test
    @Timeout(5) // Poll at most 5 seconds
    void pollUntil() throws InterruptedException {
        while (asynchronousResultNotAvailable()) {
            Thread.sleep(250); // custom poll interval
        }
        // Obtain the asynchronous result and perform assertions
    }

    private boolean asynchronousResultNotAvailable() {
        return true;
    }
}
