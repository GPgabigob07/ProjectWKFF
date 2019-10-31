package org.gpginc.ntateam.projectwkff.runtime.util;

public class WaletItem<T>
{
    protected T item;
    protected int count;

    public WaletItem(T item, int count) {
        this.item = item;
        this.count = count;
    }

    public WaletItem() {
    }

    public T consume()
    {
        this.count--;
        return item;
    }

    public int getCount() {
        return count;
    }

    public T getItem() {
        return item;
    }

    public static WaletItem newItem(Object item, int count)
    {
        WaletItem w = new WaletItem(item, count);
        return w;
    }
}
