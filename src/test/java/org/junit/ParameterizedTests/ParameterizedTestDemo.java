package org.junit.ParameterizedTests;

import org.junit.assertions.AssertionsDemo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.junit.jupiter.params.provider.EnumSource.Mode.MATCH_ALL;


/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/12 15:37
 */
public class ParameterizedTestDemo {

    @ParameterizedTest
    @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
    void palindromes(String candidate) {
        //assertTrue(StringUtils.isPalindrome(candidate));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testWithValueSource(int argument) {
        assertTrue(argument > 0 && argument < 4);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStrings(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStrings1(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    // Both variants of the nullEmptyAndBlankStrings(String) parameterized test method result in six invocations:
    // 1 for null, 1 for the empty string, and 4 for the explicit blank strings supplied via @ValueSource.


    /**
     * @EnumSource provides a convenient way to use Enum constants.
     */
    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithEnumSource(TemporalUnit unit, TestReporter reporter) {
        assertNotNull(unit);
        reporter.publishEntry("test", unit.toString());
    }

    /**
     * The annotation’s value attribute is optional. When omitted, the declared type of the first method parameter is used.
     * The test will fail if it does not reference an enum type. Thus, the value attribute is required in the above example
     * because the method parameter is declared as TemporalUnit, i.e. the interface implemented by ChronoUnit, which isn’t an enum type.
     * Changing the method parameter type to ChronoUnit allows you to omit the explicit enum type from the annotation as follows.
     */
    @ParameterizedTest
    @EnumSource
    void testWithEnumSourceWithAutoDetection(ChronoUnit unit, TestReporter reporter) {
        assertNotNull(unit);
        reporter.publishEntry(unit.toString());
    }

    /**
     * The annotation provides an optional names attribute that lets you specify which constants shall be used, like in the following example.
     * If omitted, all constants will be used.
     */
    @ParameterizedTest
    @EnumSource(names = { "DAYS", "HOURS" })
    void testWithEnumSourceInclude(ChronoUnit unit, TestReporter reporter) {
        assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit));
        reporter.publishEntry(unit.toString());
    }


    /**
     * The @EnumSource annotation also provides an optional mode attribute that enables fine-grained control over which constants are
     * passed to the test method. For example, you can exclude names from the enum constant pool or specify regular expressions as
     * in the following examples.
     */
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = { "ERAS", "FOREVER" })
    void testWithEnumSourceExclude(ChronoUnit unit) {
        assertFalse(EnumSet.of(ChronoUnit.ERAS, ChronoUnit.FOREVER).contains(unit));
    }

    @ParameterizedTest
    @EnumSource(mode = MATCH_ALL, names = "^.*DAYS$")
    void testWithEnumSourceRegex(ChronoUnit unit) {
        assertTrue(unit.name().endsWith("DAYS"));
    }

    @ParameterizedTest
    @MethodSource("stringProvider1")
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
        System.out.println(argument);
    }

    static Stream<String> stringProvider(){
        return Stream.of("Hello String Provider");
    }
    static Stream<String> stringProvider1() {
        return Stream.of("apple1", "banana1");
    }

    /**
     * If you do not explicitly provide a factory method name via @MethodSource,
     * JUnit Jupiter will search for a factory method that has the same name as the current @ParameterizedTest method by convention.
     * This is demonstrated in the following example.
     */
    @ParameterizedTest
    @MethodSource
    void testWithDefaultLocalMethodSource(String argument) {
        assertNotNull(argument);
        System.out.println(argument);
    }

    static Stream<String> testWithDefaultLocalMethodSource() {
        return Stream.of("apple", "banana");
    }

    /**
     * Streams for primitive types (DoubleStream, IntStream, and LongStream) are also supported as demonstrated by the following example.
     */
    @ParameterizedTest
    @MethodSource("range")
    void testWithRangeMethodSource(int argument) {
        assertNotEquals(9, argument);
        System.out.println(argument);
    }

    static IntStream range() {
        return IntStream.range(0, 20).skip(10);
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >=1 && num <=2);
        assertEquals(2, list.size());
    }

    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                arguments("apple", 1, Arrays.asList("a", "b")),
                arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }


    public static class ExternalMethodSourceDemo {

        @ParameterizedTest
        @MethodSource("org.junit.ParameterizedTests.ParameterizedTestDemo.StringsProviders.tinyStrings")
        public void testWithExternalMethodSource(String tinyString) {
            // test with tiny string
            System.out.println(tinyString);
        }
    }

    public static class StringsProviders {

        public static Stream<String> tinyStrings() {
            return Stream.of(".", "oo", "OOO");
        }
    }

    @ParameterizedTest
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "'lemon, lime', 0xF1"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
        System.out.println(fruit + " " + rank);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromClasspath(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromFile(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    void testWithArgumentsSource(String argument) {
        assertNotNull(argument);
    }

    public class MyArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of("apple", "banana").map(Arguments::of);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Jane, Doe, F, 1990-05-20",
            "John, Doe, M, 1990-10-22"
    })
    void testWithArgumentsAccessor(ArgumentsAccessor arguments) {
        //AssertionsDemo.Person person = new AssertionsDemo.Person(arguments.getString(0),
        //        arguments.getString(1),
        //        arguments.get(2, Gender.class),
        //        arguments.get(3, LocalDate.class));
        //
        //if (person.getFirstName().equals("Jane")) {
        //    assertEquals(Gender.F, person.getGender());
        //}
        //else {
        //    assertEquals(Gender.M, person.getGender());
        //}
        //assertEquals("Doe", person.getLastName());
        //assertEquals(1990, person.getDateOfBirth().getYear());
        System.out.println(arguments);
    }

    @ParameterizedTest
    @CsvSource({
            "Jane, Doe, F, 1990-05-20",
            "John, Doe, M, 1990-10-22"
    })
    void testWithArgumentsAggregator(@AggregateWith(PersonAggregator.class) AssertionsDemo.Person person) {
        // perform assertions against person
    }

    public class PersonAggregator implements ArgumentsAggregator {
        @Override
        public AssertionsDemo.Person aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
            //return new AssertionsDemo.Person(arguments.getString(0),
            //        arguments.getString(1),
            //        arguments.get(2, Gender.class),
            //        arguments.get(3, LocalDate.class));
            return null;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Jane, Doe, F, 1990-05-20",
            "John, Doe, M, 1990-10-22"
    })
    void testWithCustomAggregatorAnnotation(@CsvToPerson AssertionsDemo.Person person) {
        // perform assertions against person
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AggregateWith(PersonAggregator.class)
    public @interface CsvToPerson {
    }
}
