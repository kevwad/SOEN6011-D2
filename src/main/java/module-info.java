module com.soen6011.d2.soen6011d2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.soen6011.d2.soen6011d2 to javafx.fxml;
    exports com.soen6011.d2.soen6011d2;
}