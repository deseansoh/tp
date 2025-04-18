package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.Test;

public class LocalDateUtilsTest {
    @Test
    public void testFormatDateString_validInputsWithoutYear() {
        // Test for "d/m" and "dd/mm" format (should add current year)
        int currentYear = Year.now().getValue() % 100; // Last 2 digits of current year

        String date1 = "1/1";
        String expected1 = "01/01/" + currentYear;
        assertEquals(expected1, LocalDateUtils.formatDateString(date1));

        String date2 = "9/12";
        String expected2 = "09/12/" + currentYear;
        assertEquals(expected2, LocalDateUtils.formatDateString(date2));
    }

    @Test
    public void testFormatDateString_validInputsWithYear() {
        // Test for "d/m/yy" format
        String date1 = "1/1/23";
        String expected1 = "01/01/23";
        assertEquals(expected1, LocalDateUtils.formatDateString(date1));

        String date2 = "15/7/45";
        String expected2 = "15/07/45";
        assertEquals(expected2, LocalDateUtils.formatDateString(date2));
    }

    @Test
    public void testFormatDateString_withWhitespaces() {
        // Test with leading/trailing whitespaces
        int currentYear = Year.now().getValue() % 100;

        String date1 = " 2 / 2 ";
        String expected1 = "02/02/" + currentYear;
        assertEquals(expected1, LocalDateUtils.formatDateString(date1));

        String date2 = "10/ 11 /21 ";
        String expected2 = "10/11/21";
        assertEquals(expected2, LocalDateUtils.formatDateString(date2));
    }

    @Test
    public void testFormatDateString_nullInput() {
        // Test for null input (should throw NullPointerException)
        assertThrows(NullPointerException.class, () -> {
            LocalDateUtils.formatDateString(null);
        });
    }

    @Test
    public void testLocalDateParser_validDates() {
        // Test for "dd/MM/yy" format
        String date1 = "01/01/23"; // Should parse as 1st Jan 2023
        LocalDate expectedDate1 = LocalDate.of(2023, 1, 1);
        assertEquals(expectedDate1, LocalDateUtils.localDateParser(date1));

        String date2 = "25/12/25"; // Should parse as 25th Dec 2025
        LocalDate expectedDate2 = LocalDate.of(2025, 12, 25);
        assertEquals(expectedDate2, LocalDateUtils.localDateParser(date2));
    }

    @Test
    public void testLocalDateParser_invalidDates() {
        // Test invalid date formats (should throw IllegalArgumentException)
        String invalidDate1 = "32/01/23"; // Invalid day
        assertThrows(IllegalArgumentException.class, () -> {
            LocalDateUtils.localDateParser(invalidDate1);
        });

        String invalidDate2 = "00/00/00"; // Invalid date
        assertThrows(IllegalArgumentException.class, () -> {
            LocalDateUtils.localDateParser(invalidDate2);
        });
    }

    @Test
    public void testIsValidDateString_validDates() {
        assertTrue(LocalDateUtils.isValidDateString("1/1"));
        assertTrue(LocalDateUtils.isValidDateString("15/07/45"));
    }

    @Test
    public void testIsValidDateString_invalidDays() {
        assertFalse(LocalDateUtils.isValidDateString("32/01")); // Day exceeds max
        assertFalse(LocalDateUtils.isValidDateString("00/10")); // Day cannot be 0
    }

    @Test
    public void testIsValidDateString_invalidDateInMonth() {
        assertFalse(LocalDateUtils.isValidDateString("31/04")); // April has only 30 days
        assertFalse(LocalDateUtils.isValidDateString("30/02/24")); // February max 29 days
    }

    @Test
    public void testIsValidDateString_leapYearValidDate() {
        assertTrue(LocalDateUtils.isValidDateString("29/02/24"));
    }

    @Test
    public void testIsValidDateString_commonYearValidDate() {
        assertTrue(LocalDateUtils.isValidDateString("28/02/25"));
    }

    @Test
    public void testIsValidDateString_commonYearInvalidDate() {
        assertFalse(LocalDateUtils.isValidDateString("29/02/25"));
    }

    @Test
    public void testIsValidDateString_nullOrEmptyInput() {
        assertFalse(LocalDateUtils.isValidDateString("")); // Empty string
        assertThrows(NullPointerException.class, () -> {
            LocalDateUtils.isValidDateString(null);
        }); // Null input should throw exception
    }
}
