package org.example.models.items.consoles;

import org.example.models.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Console extends Item {

    protected final List<Accessory> accessories;

    public Console() {
        this.accessories = new ArrayList<>();
    }

    @Override
    public Type getItemType() {
        return Type.CONSOLE;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public static class Accessory {
        private final String name;

        public Accessory(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
