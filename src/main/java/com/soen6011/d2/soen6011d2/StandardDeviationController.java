package com.soen6011.d2.soen6011d2;

import java.net.URL;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StandardDeviationController implements Initializable {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private StandardDeviationCalculator calculator;

    @FXML
    private Label invalidDPLabel;

    @FXML
    private TextField inputField;

    @FXML
    private Label dataPointsCleared;

    @FXML
    private Button clearButton;

    @FXML
    private Button addDPButton;

    @FXML
    private Label stdDevLabel;

    @FXML
    private Label dataPointsLabel;

    @FXML
    private Label sampleStdDevLabel;

    public StandardDeviationController() {
        this.calculator = new StandardDeviationCalculator();
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        stdDevLabel.setVisible(false);
        sampleStdDevLabel.setVisible(false);
    }

    /**
     * We try to extract the double value or the value(s) which are then added to th list.
     */
    @FXML
    protected void onAddDataPointClick() {
        invalidDPLabel.setVisible(true);

        final String currentValue = inputField.getText();
        if (currentValue.isEmpty()) {
            invalidDPLabel.setVisible(true);
        }
        try {
            final double currentDouble = Double.parseDouble(currentValue);
            calculator.addDataPoint(currentDouble);
            invalidDPLabel.setText("[" + currentValue + "] has been added to the data points");
            updateCurrentPoints();
        } catch (NumberFormatException exception) {

            final String[] commaSeparated = currentValue.split(", ");
            final List<Double> convertedRankList = new ArrayList<>();
            try {
                for (final String number : commaSeparated) {
                    convertedRankList.add(Double.parseDouble(number));
                }

                final double[] dataPoints = convertedRankList.stream().mapToDouble(d -> d).toArray();
                calculator.addDataPoints(dataPoints);
                invalidDPLabel.setText("[" + currentValue + "] has been added to the data points");
                updateCurrentPoints();
            } catch (NumberFormatException exception2) {
                invalidDPLabel.setText("Please enter a valid Data Point (numerical values)!");
                invalidDPLabel.setVisible(true);
            }
        }
        inputField.clear();
    }

    /**
     * Clears the data points from the list and hides any error messages
     */
    @FXML
    protected void onClearDataPointClick() {
        calculator.clearData();
        invalidDPLabel.setVisible(false);
        stdDevLabel.setVisible(false);
        sampleStdDevLabel.setVisible(false);
        updateCurrentPoints();
    }

    /**
     * Updates the Message based on number of data points
     */
    private void updateCurrentPoints() {
        if (calculator.numberOfDataPoints() > 0) {
            dataPointsLabel.setText("Current Data Points are: [" + calculator.currentDataPoints() + "]");
        } else {
            dataPointsLabel.setText("Currently we do not have any data points");
        }
    }

    /**
     * When user clicks on Submit, calculate the standard deviation and display them at the bottom
     */
    @FXML
    protected void onSubmitDataPoints() {
        if (!calculator.hasEnoughDataPoints()) {
            System.out.println("Kevin: Not enough data points yet");
            invalidDPLabel.setVisible(true);
            invalidDPLabel.setText("Minimum 2 data points required.");
            return;
        }

        invalidDPLabel.setVisible(false);
        stdDevLabel.setVisible(true);
        sampleStdDevLabel.setVisible(true);

        final double popularStdDev = calculator.calculatePopulationStandardDeviation();
        final double sampleStdDev = calculator.calculateSampleStandardDeviation();

        stdDevLabel.setText("Population Standard Deviation is: " + DECIMAL_FORMAT.format(popularStdDev));
        sampleStdDevLabel.setText("Sample Standard Deviation is: " + DECIMAL_FORMAT.format(sampleStdDev));
    }
}