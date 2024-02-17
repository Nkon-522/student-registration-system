module org.nkon.studentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;

    opens org.nkon.studentmanagementsystem to javafx.fxml;
    exports org.nkon.studentmanagementsystem;
}