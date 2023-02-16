package com.alison.boot.t1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Rectangle {

    private double width;
    private double height;

    public double getArea() {
        return width * height;
    }
}
