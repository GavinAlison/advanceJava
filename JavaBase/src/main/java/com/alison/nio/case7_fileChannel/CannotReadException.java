package com.alison.nio.case7_fileChannel;

import java.io.IOException;

public class CannotReadException extends IOException {
    private String name;

    public CannotReadException(String name) {
        super(name);
    }
}