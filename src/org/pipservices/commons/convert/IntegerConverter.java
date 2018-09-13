package org.pipservices.commons.convert;

/**
 * Converts arbitrary values into integer  using extended conversion rules:
 * - Strings are converted to integer values
 * - DateTime: total number of milliseconds since unix epoсh
 * - Boolean: 1 for true and 0 for false
 * <p>
 * ### Example ###
 * <pre>
 * {@code
 * int value1 = IntegerConverter.toNullableInteger("ABC"); // Result: null
 * int value2 = IntegerConverter.toNullableInteger("123.456"); // Result: 123.456
 * int value3 = IntegerConverter.toNullableInteger(true); // Result: 1
 * int value4 = IntegerConverter.toNullableInteger(new Date()); // Result: current milliseconds
 * }
 * </pre>
 */
public class IntegerConverter {

	/**
	 * Converts value into integer or returns null when conversion is not possible.
	 * 
	 * @param value the value to convert.
	 * @return integer value or null when conversion is not supported.
	 * 
	 * @see LongConverter#toNullableLong(Object)
	 */
	public static Integer toNullableInteger(Object value) {
		Long result = LongConverter.toNullableLong(value);
		return result != null ? (int) ((long) result) : null;
	}

	/**
	 * Converts value into integer or returns 0 when conversion is not possible.
	 * 
	 * @param value the value to convert.
	 * @return integer value or 0 when conversion is not supported.
	 * 
	 * @see IntegerConverter#toIntegerWithDefault(Object, int)
	 */
	public static int toInteger(Object value) {
		return toIntegerWithDefault(value, 0);
	}

	/**
	 * Converts value into integer or returns default value when conversion is not
	 * possible.
	 * 
	 * @param value        the value to convert.
	 * @param defaultValue the default value.
	 * @return integer value or default when conversion is not supported.
	 * 
	 * @see IntegerConverter#toNullableInteger(Object)
	 */
	public static int toIntegerWithDefault(Object value, int defaultValue) {
		Integer result = toNullableInteger(value);
		return result != null ? (int) result : defaultValue;
	}

}
