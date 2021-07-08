package org.junit.disablingTests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/8 21:21
 */
@Disabled("Disabled until bug #99 has been fixed")
public class DisabledClassDemo {
    @Test
    void testWillBeSkipped() {
        System.out.println("hello");
    }
}
