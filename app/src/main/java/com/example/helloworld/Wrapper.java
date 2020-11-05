package com.example.helloworld;

import java.util.List;

public class Wrapper {
    List<Person> data;

    public Wrapper(List<Person> data) {
        this.data = data;
    }

    public List<Person> getData() {
        return data;
    }
}
