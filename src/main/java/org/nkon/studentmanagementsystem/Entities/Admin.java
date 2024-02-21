package org.nkon.studentmanagementsystem.Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin {
    private final StringProperty email;
    private final StringProperty password;

    public Admin(String email, String password) {
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
    }

    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty passwordProperty() {
        return password;
    }
}
