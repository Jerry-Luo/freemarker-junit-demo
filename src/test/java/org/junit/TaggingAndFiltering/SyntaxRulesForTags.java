package org.junit.TaggingAndFiltering;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/8 22:18
 */
public class SyntaxRulesForTags {

    @Tag("fast")
    @Tag("model")
    class TaggingDemo {

        @Test
        @Tag("taxes")
        void testingTaxCalculation() {
        }
    }
}
