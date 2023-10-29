package com.example.playground.kid;

public class PatientKid extends Kid {
    public PatientKid(String name, int age) {
        super(name, age);
    }
    public boolean canWait() {
        return true;
    }
}
