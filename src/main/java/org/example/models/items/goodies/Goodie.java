package org.example.models.items.goodies;

import org.example.models.items.Item;

public class Goodie extends Item {
    @Override
    public Type getItemType() {
        return Type.GOODIE;
    }
}
