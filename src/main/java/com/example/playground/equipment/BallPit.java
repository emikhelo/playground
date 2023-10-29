package com.example.playground.equipment;

import com.example.playground.PlaySite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BallPit extends Equipment {
    public static final int DEFAULT_MAXKIDS = 7;
    private int maxKids = DEFAULT_MAXKIDS;

    public BallPit(PlaySite playSite) {
        super(playSite);
    }
}
