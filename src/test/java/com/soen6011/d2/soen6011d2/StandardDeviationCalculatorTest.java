package com.soen6011.d2.soen6011d2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kevin on 2024-07-24
 */
class StandardDeviationCalculatorTest {

    @Test
    public void testPopulationStandardDeviation() {
        StandardDeviationCalculator calculator = new StandardDeviationCalculator();
        calculator.addDataPoints(new double[]{2, 4, 4, 4, 5, 5, 7, 9});
        double result = calculator.calculatePopulationStandardDeviation();
        assertEquals(2.0, result, 0.0001);
    }

    @Test
    public void testSampleStandardDeviation() {
        StandardDeviationCalculator calculator = new StandardDeviationCalculator();
        calculator.addDataPoints(new double[]{2, 4, 4, 4, 5, 5, 7, 9});
        double result = calculator.calculateSampleStandardDeviation();
        assertEquals(2.1381, result, 0.0001);
    }

    @Test
    public void testEmptyDataset() {
        assertThrows(IllegalArgumentException.class, () -> {
            StandardDeviationCalculator calculator = new StandardDeviationCalculator();
            calculator.calculatePopulationStandardDeviation();
        });
    }

    @Test
    public void testSingleDataPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            StandardDeviationCalculator calculator = new StandardDeviationCalculator();
            calculator.addDataPoint(5);
            calculator.calculateSampleStandardDeviation();
        });
    }
}