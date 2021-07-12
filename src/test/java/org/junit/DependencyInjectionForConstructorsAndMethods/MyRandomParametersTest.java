package org.junit.DependencyInjectionForConstructorsAndMethods;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * For real-world use cases, check out the source code for the MockitoExtension and the SpringExtension.
 * https://github.com/mockito/mockito/blob/release/2.x/subprojects/junit-jupiter/src/main/java/org/mockito/junit/jupiter/MockitoExtension.java
 * https://github.com/spring-projects/spring-framework/tree/HEAD/spring-test/src/main/java/org/springframework/test/context/junit/jupiter/SpringExtension.java
 *
 * When the type of the parameter to inject is the only condition for your ParameterResolver, you can use the generic TypeBasedParameterResolver base class.
 * The supportsParameters method is implemented behind the scenes and supports parameterized types.
 *
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/12 9:53
 */
//@ExtendWith(RandomParametersExtension.class)
public class MyRandomParametersTest {
    //@Test
    //void injectsInteger(@Random int i, @Random int j) {
    //    assertNotEquals(i, j);
    //}
    //
    //@Test
    //void injectsDouble(@Random double d) {
    //    assertEquals(0.0, d, 1.0);
    //}
}
