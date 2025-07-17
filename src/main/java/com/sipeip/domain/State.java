package com.sipeip.domain;

public enum State {
    ACTIVE("ACTIVO"),
    INACTIVE("INACTIVO");

    private final String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
