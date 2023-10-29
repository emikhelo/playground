package com.example.playground;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Playground {
    private List<PlaySite> playSites = new ArrayList<>();

    public void addPlaySite(PlaySite playSite) {
        playSites.add(playSite);
    }

    public int getVisitorsForToday() {
        return playSites.stream()
                .map(PlaySite::getVisitorsForToday)
                .flatMap(set -> set.stream())
                .collect(Collectors.toSet())
                .size();
    }
}

