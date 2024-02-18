module org.nkon.studentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;

    opens org.nkon.studentmanagementsystem to javafx.fxml;
    exports org.nkon.studentmanagementsystem;
    exports org.nkon.studentmanagementsystem.Managers;
    opens org.nkon.studentmanagementsystem.Managers to javafx.fxml;
    exports org.nkon.studentmanagementsystem.Controllers;
    opens org.nkon.studentmanagementsystem.Controllers to javafx.fxml;
    exports org.nkon.studentmanagementsystem.Mediators;
    opens org.nkon.studentmanagementsystem.Mediators to javafx.fxml;
}