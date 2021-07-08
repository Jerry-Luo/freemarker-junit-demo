package org.junit.conditions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/8 22:11
 */
public class CustomConditions {

    @Test
    @EnabledIf("customCondition")
    void enabled() {
        // ...
    }

    @Test
    @DisabledIf("customCondition")
    void disabled() {
        // ...
    }

    boolean customCondition() {
        return true;
    }

    /*
     * When @EnabledIf or @DisabledIf is used at class level, the condition method must always be static.
     * Condition methods located in external classes must also be static.
     * In any other case, you can use both static or instance methods.
     */
}
