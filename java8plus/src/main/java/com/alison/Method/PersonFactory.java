package com.alison.Method;

public interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}