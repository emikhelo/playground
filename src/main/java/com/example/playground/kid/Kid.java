package com.example.playground.kid;

import com.example.playground.PlaySite;
import com.example.playground.equipment.Equipment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Kid {
    private String name;
    private Integer age;
    private String ticketNumber;
    private KidStatus status = KidStatus.OUTSIDE;
    private PlaySite currentPlaySite;
    private Equipment currentEquipment;

    public Kid(String name, Integer age) {
        this.name = name;
        this.age = age;
        // Could elaborate this solution to issue ticket when actually entering play at the PlaySite,
        // or ensure that tickets are only valid on a given day
        this.ticketNumber = issueTicket();
    }

    public Kid(String name) {
        this(name, null);
    }
    public boolean canWait() {
        return new Random().nextBoolean();
    }

    public void letOutside() {
        setStatus(KidStatus.OUTSIDE);
        currentEquipment.removeKid(this);
        currentEquipment = null;
        currentPlaySite = null;
    }

    public void startPlay(PlaySite playSite) {
        chooseEquipment(playSite.getEquipmentList());
        setStatus(KidStatus.PLAYING);
    }

    public void chooseEquipment(List<Equipment> equipmentList) {
        List<Equipment> availableEquipment = equipmentList.stream().filter(Equipment::isAvailable).collect(Collectors.toList());

        Random random = new Random();
        Equipment equipment = availableEquipment.get(random.nextInt(availableEquipment.size()));
        currentEquipment = equipment;
        equipment.addKid(this);
    }

    // Could elaborate this to meaningfully number tickets
    private String issueTicket() {
        return UUID.randomUUID().toString();
    }
}



