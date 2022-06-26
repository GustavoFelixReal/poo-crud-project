module Test {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.validation;
    requires java.naming;

    opens src.view;
    opens src.controller;
    opens src.model;
}