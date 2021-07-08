package org.junit.conditions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/8 22:08
 */
public class EnvironmentVariableConditions {

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer() {
        // ...
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    void notOnDeveloperWorkstation() {
        // ...
    }

    /*
     * As of JUnit Jupiter 5.6, @EnabledIfEnvironmentVariable and @DisabledIfEnvironmentVariable are repeatable annotations.
     * Consequently, these annotations may be declared multiple times on a test interface, test class, or test method.
     * Specifically, these annotations will be found if they are directly present, indirectly present, or meta-present on a given element.
     */
}
