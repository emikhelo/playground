package com.example.playground.equipment;

import com.example.playground.PlaySite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slide extends Equipment {
    public static final int DEFAULT_MAXKIDS = 5;
    private int maxKids = DEFAULT_MAXKIDS;

    public Slide(PlaySite playSite) {
        super(playSite);
    }
}
