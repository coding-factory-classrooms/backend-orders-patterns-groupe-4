package org.example.models.items.consoles;

import java.util.ArrayList;
import java.util.List;

public class Switch extends Console {
    public Switch() {
        this.setName("Switch");
        this.accessories.addAll(this.getConsoleAccessories());
    }

    private List<Accessory> getConsoleAccessories() {
        List<Accessory> accessories = new ArrayList<>();
        accessories.add(new Accessory("Joycons"));
        accessories.add(new Accessory("Dock"));
        accessories.add(new Accessory("Adaptateur GC"));
        return accessories;
    }
}
