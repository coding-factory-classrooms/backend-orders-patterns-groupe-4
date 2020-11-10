package org.example.models.items;

import org.example.models.items.Item;

public class Goodie extends Item {
    @Override
    public Type getItemType() {
        return Type.GOODIE;
    }
}
