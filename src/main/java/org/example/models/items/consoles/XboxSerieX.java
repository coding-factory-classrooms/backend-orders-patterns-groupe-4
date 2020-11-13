package org.example.models.items.consoles;

import java.util.ArrayList;
import java.util.List;

public class XboxSerieX extends Console {
    public XboxSerieX() {
        this.setName("Xbox Serie X");
        this.accessories.addAll(this.getConsoleAccessories());
    }

    private List<Accessory> getConsoleAccessories() {
        List<Accessory> accessories = new ArrayList<>();
        accessories.add(new Accessory("Manette"));
        accessories.add(new Accessory("Câble d'alimentation"));
        accessories.add(new Accessory("Stickers"));
        return accessories;
    }
}
