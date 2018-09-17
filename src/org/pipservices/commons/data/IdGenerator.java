package org.pipservices.commons.data;

import java.util.*;

/**
 * Helper class to generate unique object IDs.
 * It supports two types of IDs: long and short.
 * 
 * Long IDs are string GUIDs. They are globally unique and 32-character long.
 * 
 * ShortIDs are just 9-digit random numbers. They are not guaranteed be unique.
 * <p>
 * ### Example ###
 * <pre>
 * {@code
 * IdGenerator.nextLong();      // Possible result: "234ab342c56a2b49c2ab42bf23ff991ac"
 * IdGenerator.nextShort();     // Possible result: "23495247"
 * }
 * </pre>
 */
public class IdGenerator {
	/**
	 * Generates a random 9-digit random ID (code).
	 * 
	 * Remember: The returned value is not guaranteed to be unique.
	 * 
	 * @return a generated random 9-digit code
	 */
	public static String nextShort() {
		return new Long((long) Math.floor(100000000 + Math.random() * 899999999)).toString();
	}

	/**
	 * Generates a globally unique 32-digit object ID. The value is a string
	 * representation of a GUID value.
	 * 
	 * @return a generated 32-digit object ID
	 */
	public static String nextLong() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
