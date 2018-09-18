package org.pipservices.commons.validate;

import java.util.*;

import org.pipservices.commons.errors.*;

/**
 * Errors in schema validation.
 * 
 * Validation errors are usually generated based in ValidationResult.
 * If using strict mode, warnings will also raise validation exceptions.
 * 
 * @see BadRequestException
 * @see ValidationResult
 */
public class ValidationException extends BadRequestException {
	private static final long serialVersionUID = -1459801864235223845L;

	/**
	 * Creates a new instance of validation exception and assigns its values.
	 * 
	 * @param correlationId (optional) a unique transaction id to trace execution
	 *                       through call chain.
	 * @param results        (optional) a list of validation results
	 * 
	 * @see ValidationResult
	 */
	public ValidationException(String correlationId, List<ValidationResult> results) {
		this(correlationId, composeMessage(results));
		this.withDetails("results", results);
	}

	/**
	 * Creates a new instance of validation exception and assigns its values.
	 * 
	 * @param correlationId (optional) a unique transaction id to trace execution
	 *                       through call chain.
	 * @param message        (optional) a human-readable description of the error.
	 * 
	 * @see ValidationResult
	 */
	public ValidationException(String correlationId, String message) {
		super(correlationId, "INVALID_DATA", message);
	}

	/**
	 * Composes human readable error message based on validation results.
	 * 
	 * @param results a list of validation results.
	 * @return a composed error message.
	 * 
	 * @see ValidationResult
	 */
	public static String composeMessage(List<ValidationResult> results) {
		StringBuilder builder = new StringBuilder();
		builder.append("Validation failed");

		if (results != null && results.size() > 0) {
			boolean first = true;
			for (ValidationResult result : results) {
				if (result.getType() != ValidationResultType.Information) {
					if (!first)
						builder.append(": ");
					else
						builder.append(", ");
					builder.append(result.getMessage());
					first = false;
				}
			}
		}

		return builder.toString();
	}

	/**
	 * Creates a new ValidationException based on errors in validation results. If
	 * validation results have no errors, than null is returned.
	 * 
	 * @param correlationId (optional) transaction id to trace execution through
	 *                      call chain.
	 * @param results       list of validation results that may contain errors
	 * @param strict        true to treat warnings as errors.
	 * @return a newly created ValidationException or null if no errors in found.
	 * @throws ValidationException when errors occured in validation.
	 * 
	 * @see ValidationResult
	 */
	public static ValidationException fromResults(String correlationId, List<ValidationResult> results, boolean strict)
			throws ValidationException {
		boolean hasErrors = false;
		for (ValidationResult result : results) {
			if (result.getType() == ValidationResultType.Error)
				hasErrors = true;
			if (strict && result.getType() == ValidationResultType.Warning)
				hasErrors = true;
		}

		return hasErrors ? new ValidationException(correlationId, results) : null;
	}

	/**
	 * Throws ValidationException based on errors in validation results. If
	 * validation results have no errors, than no exception is thrown.
	 * 
	 * @param correlationId (optional) transaction id to trace execution through
	 *                      call chain.
	 * @param results       list of validation results that may contain errors
	 * @param strict        true to treat warnings as errors.
	 * @throws ValidationException when errors occured in validation.
	 * 
	 * @see ValidationResult
	 * @see ValidationException
	 */
	public static void throwExceptionIfNeeded(String correlationId, List<ValidationResult> results, boolean strict)
			throws ValidationException {
		ValidationException ex = fromResults(correlationId, results, strict);
		if (ex != null)
			throw ex;
	}

}
