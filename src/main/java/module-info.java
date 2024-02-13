module org.nkon.studentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.nkon.studentmanagementsystem to javafx.fxml;
    exports org.nkon.studentmanagementsystem;
}