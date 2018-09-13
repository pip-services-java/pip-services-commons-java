package org.pipservices.commons.convert;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Converts arbitrary values into strings using extended conversion rules:
 * - Numbers: are converted with '.' as decimal point
 * - DateTime: using ISO format
 * - Boolean: "true" for true and "false" for false
 * - Arrays: as comma-separated list
 * - Other objects: using toString() method
 * <p>
 * ### Example ###
 * <pre>
 * {@code
 * String value1 = StringConverter.ToString(123.456); // Result: "123.456"
 * String value2 = StringConverter.ToString(true); // Result: "true"
 * String value3 = StringConverter.ToString(ZonedDateTime.now()); // Result: "2018-01-01T00:00:00.00"
 * String value4 = StringConverter.ToString(new int[]{1, 2, 3}); // Result: "1,2,3"
 * }
 * </pre>
 */
public class StringConverter {

	/**
	 * Converts value into string or returns null when value is null.
	 * 
	 * @param value the value to convert.
	 * @return string value or null when value is null.
	 */
	public static String toNullableString(Object value) {
		// Shortcuts
		if (value == null)
			return null;
		if (value instanceof String)
			return (String) value;

		// Legacy and new dates
		if (value instanceof Date)
			value = ZonedDateTime.ofInstant(((Date) value).toInstant(), ZoneId.systemDefault());
		if (value instanceof Calendar) {
			value = ZonedDateTime.ofInstant(((Calendar) value).toInstant(),
					((Calendar) value).getTimeZone().toZoneId());
		}
		if (value instanceof Duration)
			value = ((Duration) value).toMillis();
		if (value instanceof Instant)
			value = ZonedDateTime.ofInstant((Instant) value, ZoneId.systemDefault());
		if (value instanceof LocalDateTime)
			value = ZonedDateTime.of((LocalDateTime) value, ZoneId.systemDefault());
		if (value instanceof LocalDate)
			value = ZonedDateTime.of((LocalDate) value, LocalTime.of(0, 0), ZoneId.systemDefault());
		if (value instanceof ZonedDateTime)
			return ((ZonedDateTime) value).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

		// Everything else
		return value.toString();
	}

	/**
	 * Converts value into string or returns "" when value is null.
	 * 
	 * @param value the value to convert.
	 * @return string value or "" when value is null.
	 * 
	 * @see StringConverter#toStringWithDefault(Object, String)
	 */
	public static String toString(Object value) {
		return toStringWithDefault(value, "");
	}

	/**
	 * Converts value into string or returns default when value is null.
	 * 
	 * @param value        the value to convert.
	 * @param defaultValue the default value.
	 * @return string value or default when value is null.
	 * 
	 * @see StringConverter#toNullableString(Object)
	 */
	public static String toStringWithDefault(Object value, String defaultValue) {
		String result = toNullableString(value);
		return result != null ? result : defaultValue;
	}

}
