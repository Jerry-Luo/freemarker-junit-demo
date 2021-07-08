package org.junit.conditions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/8 21:41
 */
public class SystemPropertyConditions {

    /**
     * The value supplied via the matches attribute will be interpreted as a regular expression.
     */
    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
        // ...
    }

    /**
     * As of JUnit Jupiter 5.6, @EnabledIfSystemProperty and @DisabledIfSystemProperty are repeatable annotations.
     * Consequently, these annotations may be declared multiple times on a test interface, test class, or test method.
     * Specifically, these annotations will be found if they are directly present, indirectly present, or meta-present on a given element.
     */
    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
        // ...
    }

}
