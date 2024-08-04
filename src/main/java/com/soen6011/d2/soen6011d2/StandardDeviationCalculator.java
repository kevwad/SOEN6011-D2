/**
 * @author kevin on 2024-07-11
 */
package com.soen6011.d2.soen6011d2;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StandardDeviationCalculator {

    /**
     * container for the list of data points.
     */
    private List<Double> dataPoints;

    public StandardDeviationCalculator() {
        dataPoints = new ArrayList<>();
    }

    /**
     * Adds the value to the private list
     * @param value value to be added
     */
    public void addDataPoint(final double value) {
        dataPoints.add(value);
    }

    /**
     * adds a list of data points to the private list
     * @param values set of data points to be added
     */
    public void addDataPoints(final double[] values) {
        for (final double value : values) {
            dataPoints.add(value);
        }
    }

    /**
     * @return true if more than 1 data points, else false
     */
    public boolean hasEnoughDataPoints() {
        return dataPoints.size() > 1;
    }

    /**
     * @return number of data points
     */
    public int numberOfDataPoints() {
        return dataPoints.size();
    }

    /**
     * @return Generates the list of comma separated data points.
     */
    public String currentDataPoints() {
        String asString = "-";
        if (!dataPoints.isEmpty()) {
            final StringBuilder dpString = new StringBuilder();
            final DecimalFormat decimalFormatter = new DecimalFormat("0.00");
            int index = 0;
            for (final double dataPoint: dataPoints) {
                dpString.append(decimalFormatter.format(dataPoint));
                if (index != dataPoints.size() - 1) {
                    dpString.append(',');
                }
                index++;
            }
            asString = dpString.toString();
        }

        return asString;
    }

    /**
     * @return  Returns the population standard deviation
     */
    public double calculatePopulationStandardDeviation() {
        if (dataPoints.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty.");
        }
        final double mean = calculateMean();
        double squaredDeviations = 0.0;
        for (final double value : dataPoints) {
            squaredDeviations += Math.pow(value - mean, 2);
        }
        return Math.sqrt(squaredDeviations / dataPoints.size());
    }

    /**
     * Returns the sample standard deviation
     * @return sample standard deviation
     */
    public double calculateSampleStandardDeviation() {
        if (dataPoints.size() < 2) {
            throw new IllegalArgumentException("Dataset must contain at least two data points.");
        }
        final double mean = calculateMean();
        double squaredDeviations = 0.0;
        for (final double value : dataPoints) {
            squaredDeviations += Math.pow(value - mean, 2);
        }
        return Math.sqrt(squaredDeviations / (dataPoints.size() - 1));
    }

    /**
     * Clear all the data points
     */
    public void clearData() {
        dataPoints.clear();
    }

    /**
     * Calculates the mean which will be used to calculate the standard deviation
     * @return mean of the data points
     */
    private double calculateMean() {
        double sum = 0.0;
        for (final double value : dataPoints) {
            sum += value;
        }
        return sum / dataPoints.size();
    }
}
