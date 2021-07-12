package org.junit.TestInterfacesAndDefaultMethods.writeTestsForInterfaceContracts;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/12 11:26
 */
public interface Testable<T> {
    T createValue();
}
