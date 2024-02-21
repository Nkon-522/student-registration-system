package org.nkon.studentmanagementsystem.Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty name;
    private final StringProperty fatherName;
    private final StringProperty blood;
    private final StringProperty phone;
    private final StringProperty city;
    private final IntegerProperty class_;

    public Student(String name, String fatherName, String blood, String phone, String city, Integer class_) {
        this.name = new SimpleStringProperty(name);
        this.fatherName = new SimpleStringProperty(fatherName);
        this.blood = new SimpleStringProperty(blood);
        this.phone = new SimpleStringProperty(phone);
        this.city = new SimpleStringProperty(city);
        this.class_ = new SimpleIntegerProperty(class_);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty fatherNameProperty() {
        return fatherName;
    }

    public StringProperty bloodProperty() {
        return blood;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public IntegerProperty classProperty() {
        return class_;
    }
}
