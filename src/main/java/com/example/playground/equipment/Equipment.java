package com.example.playground.equipment;

import com.example.playground.kid.Kid;
import com.example.playground.PlaySite;

import static java.lang.String.format;

public abstract class Equipment {
    // Number of playing kids assigned to this Equipment
    private int kidsAssigned = 0;
    private PlaySite playSite;

    public Equipment(PlaySite playSite) {
        this.playSite = playSite;
        playSite.addEquipment(this);
    }
    public abstract int getMaxKids();
    /*
     * Number of kids of those assigned that are engaged in play.
     * See how DoubleSwing overrides this.
     * This method can also be used to calculate utilization differently.
     */
    public int getKidsEngaged() {
        return kidsAssigned;
    }

    public int getKidsAssigned() {
        return kidsAssigned;
    }

    public double calcUtilization() {
        int maxKids = getMaxKids();
        if (maxKids == 0) {
            throw new RuntimeException(format("Property maxKids must be not null for equipment: %s", this.getClass()));
        }
        return getKidsEngaged()/ maxKids;
    }

    public boolean isAvailable() {
        return kidsAssigned < getMaxKids();
    }
    public void addKid(Kid kid) {
        kidsAssigned++;
    }

    public void removeKid(Kid kid) {
        kidsAssigned--;
    }
}
