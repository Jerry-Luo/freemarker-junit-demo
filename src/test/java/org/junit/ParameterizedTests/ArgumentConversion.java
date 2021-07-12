package org.junit.ParameterizedTests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.converter.TypedArgumentConverter;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/12 17:29
 */
public class ArgumentConversion {

    /**
     * String instances are implicitly converted to the following target types.
     *
     * Decimal, hexadecimal, and octal String literals will be converted to their integral types:
     * byte, short, int, long, and their boxed counterparts.
     */
    @ParameterizedTest
    @ValueSource(strings = "SECONDS")
    void testWithImplicitArgumentConversion(ChronoUnit argument) {
        assertNotNull(argument.name());
    }

    @ParameterizedTest
    @ValueSource(strings = "42 Cats")
    void testWithImplicitFallbackArgumentConversion(Book book) {
        assertEquals("42 Cats", book.getTitle());
    }

    public static class Book {

        private final String title;

        private Book(String title) {
            this.title = title;
        }

        public static  Book fromTitle(String title) {
            return new Book(title);
        }

        public String getTitle() {
            return this.title;
        }
    }


    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithExplicitArgumentConversion(@ConvertWith(ToStringArgumentConverter.class) String argument) {

        assertNotNull(ChronoUnit.valueOf(argument));
    }

    public static class ToStringArgumentConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) {
            assertEquals(String.class, targetType, "Can only convert to String");
            if (source instanceof Enum<?>) {
                return ((Enum<?>) source).name();
            }
            return String.valueOf(source);
        }
    }

    /**
     * If the converter is only meant to convert one type to another, you can extend TypedArgumentConverter to avoid boilerplate type checks.
     */
    public class ToLengthArgumentConverter extends TypedArgumentConverter<String, Integer> {

        protected ToLengthArgumentConverter() {
            super(String.class, Integer.class);
        }

        @Override
        protected Integer convert(String source) {
            return source.length();
        }

    }

    /**
     * Explicit argument converters are meant to be implemented by test and extension authors.
     * Thus, junit-jupiter-params only provides a single explicit argument converter that may also serve as a reference implementation:
     * JavaTimeArgumentConverter. It is used via the composed annotation JavaTimeConversionPattern.
     */
    @ParameterizedTest
    @ValueSource(strings = { "01.01.2017", "31.12.2017" })
    void testWithExplicitJavaTimeConverter(@JavaTimeConversionPattern("dd.MM.yyyy") LocalDate argument) {

        assertEquals(2017, argument.getYear());
    }
}
