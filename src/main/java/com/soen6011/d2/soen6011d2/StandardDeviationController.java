package com.soen6011.d2.soen6011d2;

import com.soen6011.d2.soen6011d2.util.I18N;

import java.net.URL;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StandardDeviationController implements Initializable {

    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");

    private StandardDeviationCalculator calculator;

    @FXML
    private Label invalidDataPointLabel;

    @FXML
    private TextField inputField;

    @FXML
    private Label dataPointsCleared;

    @FXML
    private Button clearButton;

    @FXML
    private Button addDataPointButton;

    @FXML
    private Label populationStdDevLabel;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populationStdDevLabel.setVisible(false);
        sampleStdDevLabel.setVisible(false);
    }

    /**
     * We try to extract the double value or the value(s) which are then added to th list.
     */
    @FXML
    protected void onAddDataPointClick() {
        invalidDataPointLabel.setVisible(true);

        String currentValue = inputField.getText();
        if (currentValue.isEmpty()) {
            invalidDataPointLabel.setVisible(true);
        }
        try {
            double currentDoubleValue = Double.parseDouble(currentValue);
            calculator.addDataPoint(currentDoubleValue);
            invalidDataPointLabel.setText("[" + currentValue + "] has been added to the data points");
            updateCurrentPoints();
        } catch (NumberFormatException exception) {

            String[] commaSeparated = currentValue.split(", ");
            List<Double> convertedRankList = new ArrayList<Double>();
            try {
                for (String number : commaSeparated) {
                    convertedRankList.add(Double.parseDouble(number));
                }

                double[] dataPoints = convertedRankList.stream().mapToDouble(d -> d).toArray();
                calculator.addDataPoints(dataPoints);
                invalidDataPointLabel.setText("[" + currentValue + "] has been added to the data points");
                updateCurrentPoints();
            } catch (NumberFormatException exception2) {
                invalidDataPointLabel.setText("Please enter a valid Data Point (numerical values)!");
                invalidDataPointLabel.setVisible(true);
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
        invalidDataPointLabel.setVisible(false);
        populationStdDevLabel.setVisible(false);
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
            invalidDataPointLabel.setVisible(true);
            invalidDataPointLabel.setText("Minimum 2 data points required.");
            return;
        }

        invalidDataPointLabel.setVisible(false);
        populationStdDevLabel.setVisible(true);
        sampleStdDevLabel.setVisible(true);

        double popularStdDev = calculator.calculatePopulationStandardDeviation();
        double sampleStdDev = calculator.calculateSampleStandardDeviation();

        populationStdDevLabel.setText("Population Standard Deviation is: " + decimalFormatter.format(popularStdDev));
        sampleStdDevLabel.setText("Sample Standard Deviation is: " + decimalFormatter.format(sampleStdDev));
    }
}