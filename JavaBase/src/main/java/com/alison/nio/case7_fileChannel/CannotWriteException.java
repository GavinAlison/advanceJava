package com.alison.nio.case4_fileChannel;

import java.io.IOException;


public class CannotWriteException extends IOException {
    private String name;

    public CannotWriteException(String name) {
        super(name);
    }
}
