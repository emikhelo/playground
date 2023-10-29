package com.example.playground.equipment;

import com.example.playground.PlaySite;
import lombok.Getter;

@Getter
public class DoubleSwing extends Equipment {
    private static final int MAXKIDS = 2;
    private final int maxKids = 2;

    public DoubleSwing(PlaySite playSite) {
        super(playSite);
    }

    @Override
    public int getKidsEngaged() {
        return (getKidsAssigned() < MAXKIDS) ? 0 : MAXKIDS;
    }
}
