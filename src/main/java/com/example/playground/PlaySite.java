package com.example.playground;

import com.example.playground.equipment.Equipment;
import com.example.playground.kid.Kid;
import com.example.playground.kid.KidStatus;

import java.util.*;

import static java.lang.String.format;

public class PlaySite {
    private String name;
    private List<Equipment> equipmentList = new ArrayList<>();
    private Map<String, Kid> kidsPlaying = new HashMap<>();
    private Queue<Kid> kidsQueued = new LinkedList<>();
    private VisitorRegistry visitorRegistry = new VisitorRegistry();
    private UtilizationCalculator utilizationCalculator = UtilizationCalculatorImpl.getInstance();

    public PlaySite(Playground playground, String name) {
        this.name = name;
        playground.addPlaySite(this);
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void addEquipment(Equipment equipment) {
        this.equipmentList.add(equipment);
    }
    public void setUtilizationCalculator(UtilizationCalculator utilizationCalculator) {
        this.utilizationCalculator = utilizationCalculator;
    }

    public int getMaxKids() {
        return equipmentList.stream().mapToInt(Equipment::getMaxKids).sum();
    }

    public boolean addOrQueueKid(Kid kid) {
        KidStatus status = kid.getStatus();
        if (status != KidStatus.OUTSIDE) {
            throw new KidException(
                    format("Cannot add kid to play site! Kid's status is %s, please remove kid from play site first.",
                    status));
        }
        if (kidsPlaying.size() < getMaxKids()) {
            addKidToPlay(kid);
        } else if (kid.canWait()) {
            kidsQueued.add(kid);
            kid.setStatus(KidStatus.QUEUED);
        } else {
            return false;
        }
        kid.setCurrentPlaySite(this);
        return true;
    }

    public void removeKid(Kid kid) {
        if (kid.getStatus() == KidStatus.PLAYING) {
            removeKidFromPlayInviteAnother(kid);
        } else if (kid.getStatus() == KidStatus.QUEUED) {
            exitQueue(kid);
        } else {
            throw new KidException(format("Cannot remove kid %s from play site because kid is outside!", kid.getName()));
        }
    }

    private void addKidToPlay(Kid kid) {
        kidsPlaying.put(kid.getTicketNumber(), kid);
        kid.startPlay(this);
        visitorRegistry.registerVisitor(kid);
    }

    private void exitQueue(Kid kid) {
        kidsQueued.removeIf(k -> k.getTicketNumber().equals(kid.getTicketNumber()));
        kid.letOutside();
    }

    private void removeKidFromPlayInviteAnother(Kid kid) {
        String ticketNumber = kid.getTicketNumber();

        if (!kidsPlaying.containsKey(ticketNumber)) {
            throw new KidException(format("Kid named %s is not on playground %s", kid.getName(), this.name));
        }
        kidsPlaying.remove(ticketNumber);
        kid.letOutside();

        if (kidsQueued.size() > 0) {
            Kid newKid = kidsQueued.remove();
            addKidToPlay(newKid);
        }
    }

    public Set<String> getVisitorsForToday() {
        return visitorRegistry.getVisitorsForToday();
    }

    public int calcUtilizationPercent() {
        double utilization = utilizationCalculator.calcUtilization(equipmentList);
        return (int) Math.round(utilization * 100);
    }
}