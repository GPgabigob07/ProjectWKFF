package org.gpginc.ntateam.projectwkff.runtime.util;

import java.util.ArrayList;
import java.util.List;

public class Walet<T extends WaletItem>
{
    public List<T> items = new ArrayList<>();

    public Walet() {}

    public Walet addItem(T item){
        this.items.add(item);
        return this;
    }
}
