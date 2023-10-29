package com.example.playground.kid;

public class ImpatientKid extends Kid {
    public ImpatientKid(String name, int age) {
        super(name, age);
    }
    public boolean canWait() {
        return false;
    }
}
