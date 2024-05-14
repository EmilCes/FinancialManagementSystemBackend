package com.softdev.fmsb.creditType.model;

public enum TermType {
    Semanal("Semanal"),
    Quincenal("Quincenal"),
    Mensual("Mensual");

    private final String description;

    TermType(String description) {
        this.description = description;
    }
}