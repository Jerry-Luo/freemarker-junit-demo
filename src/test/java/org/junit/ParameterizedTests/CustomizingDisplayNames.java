package org.junit.ParameterizedTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/13 10:19
 */
public class CustomizingDisplayNames {

    @DisplayName("Display name of container")
    @ParameterizedTest(name = "{index} ==> the rank of ''{0}'' is {1}")
    @CsvSource({ "apple, 1", "banana, 2", "'lemon, lime', 3" })
    void testWithCustomDisplayNames(String fruit, int rank) {
    }

}
