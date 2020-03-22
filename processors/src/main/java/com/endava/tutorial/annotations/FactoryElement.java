package com.endava.tutorial.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target(value = TYPE)
@Retention(value = SOURCE)
public @interface FactoryElement {
    String value();
}
