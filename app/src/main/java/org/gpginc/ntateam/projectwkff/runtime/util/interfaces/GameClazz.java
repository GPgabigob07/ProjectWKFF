package org.gpginc.ntateam.projectwkff.runtime.util.interfaces;

import android.os.Parcelable;

public interface GameClazz extends Parcelable
{
    <T extends GameClazz> T newInstance();
    <T extends GameClazz> T base();
}
