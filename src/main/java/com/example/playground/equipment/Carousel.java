package com.example.playground.equipment;

import com.example.playground.PlaySite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carousel extends Equipment {
    public static final int DEFAULT_MAXKIDS = 6;
    private int maxKids = DEFAULT_MAXKIDS;

    public Carousel(PlaySite playSite) {
        super(playSite);
    }
}
