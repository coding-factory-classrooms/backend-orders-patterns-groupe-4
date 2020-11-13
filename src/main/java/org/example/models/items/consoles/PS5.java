package org.example.models.items.consoles;

import java.util.ArrayList;
import java.util.List;

public class PS5 extends Console {
    public PS5() {
        this.setName("PS5");
        this.accessories.addAll(this.getConsoleAccessories());
    }

    private List<Accessory> getConsoleAccessories() {
        List<Accessory> accessories = new ArrayList<>();
        accessories.add(new Accessory("Manette"));
        accessories.add(new Accessory("Câble d'alimentation"));
        return accessories;
    }
}
