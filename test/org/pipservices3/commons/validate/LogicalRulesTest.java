package org.pipservices3.commons.validate;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.*;

public class LogicalRulesTest {
    @Test
    public void TestOrRule() {
        Schema schema = new Schema().withRule(
            new OrRule(
                new ValueComparisonRule("=", 1),
                new ValueComparisonRule("=", 2)
            )
        );
        List<ValidationResult> result = schema.validate(-100);
        assertEquals(2, result.size());

        result = schema.validate(1);
        assertEquals(0, result.size());

        result = schema.validate(200);
        assertEquals(2, result.size());
    }

    @Test
    public void TestAndRule() {
        Schema schema = new Schema().withRule(
            new AndRule(
                new ValueComparisonRule(">", 0),
                new ValueComparisonRule("<", 200)
            )
        );
        List<ValidationResult> result = schema.validate(-100);
        assertEquals(1, result.size());

        result = schema.validate(100);
        assertEquals(0, result.size());

        result = schema.validate(200);
        assertEquals(1, result.size());
    }
}