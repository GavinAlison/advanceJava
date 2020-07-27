package com.alison.Annotations;

import java.lang.annotation.*;

@Repeatable(Hints.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
public @interface Hint {
    String value();
}
