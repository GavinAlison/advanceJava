package com.alison.Annotations;

import java.lang.annotation.Repeatable;

@Repeatable(Hints.class)
public @interface Hint {
    String value();
}
