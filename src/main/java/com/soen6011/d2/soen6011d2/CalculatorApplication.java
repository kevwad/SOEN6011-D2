package com.soen6011.d2.soen6011d2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle resources = ResourceBundle.getBundle("com.soen6011.d2.soen6011d2.i18n");
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("calculator-view.fxml"), resources);
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("40261875 - F8 - Standard Deviation!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}