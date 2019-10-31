package org.gpginc.ntateam.projectwkff.runtime.util;

import android.os.Parcel;
import android.os.Parcelable;

import org.gpginc.ntateam.projectwkff.runtime.Effect;
import org.gpginc.ntateam.projectwkff.runtime.Main;

import java.util.Random;

public class EffectWalet extends Walet<EffectWalet.EffectWaletItem> implements Parcelable
{
    public EffectWalet(Parcel in)
    {
        items = in.createTypedArrayList(EffectWaletItem.CREATOR);
    }

    public static final Creator<EffectWalet> CREATOR = new Creator<EffectWalet>() {
        @Override
        public EffectWalet createFromParcel(Parcel in) {
            return new EffectWalet(in);
        }

        @Override
        public EffectWalet[] newArray(int size) {
            return new EffectWalet[size];
        }
    };

    public EffectWalet() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeTypedList(this.items);
    }

    public static class EffectWaletItem extends WaletItem<Effect> implements Parcelable
    {

        public EffectWaletItem(Effect item, int count) {
            super(item, count);
        }

        protected EffectWaletItem(Parcel in) {
            int name = in.readInt();
            item = in.readParcelable(Main.EFX_MAP.get(name).getClass().getClassLoader());
            count = in.readInt();
        }

        public static final Creator<EffectWaletItem> CREATOR = new Creator<EffectWaletItem>() {
            @Override
            public EffectWaletItem createFromParcel(Parcel in) {
                return new EffectWaletItem(in);
            }

            @Override
            public EffectWaletItem[] newArray(int size) {
                return new EffectWaletItem[size];
            }
        };

        @Override
        public Effect getItem() {
            return super.getItem();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeInt(this.item.getName());
            dest.writeParcelable(this.item, flags);
            dest.writeInt(count);
        }
    }
    public EffectWalet random()
    {
        for(int i = 1; i < 4; i ++) {
            Effect e = Main.EFX.get(new Random().nextInt(Main.EFX.size()));
            this.addItem(new EffectWalet.EffectWaletItem(e.newInstance(), new Random().nextInt(i + 1)));
        }
        return this;
    }
}
