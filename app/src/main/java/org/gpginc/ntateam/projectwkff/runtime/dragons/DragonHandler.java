package org.gpginc.ntateam.projectwkff.runtime.dragons;

import android.os.Handler;

import androidx.annotation.Nullable;

import org.gpginc.ntateam.projectwkff.GameFlux;
import org.gpginc.ntateam.projectwkff.R;
import org.gpginc.ntateam.projectwkff.databinding.ListItemEventsBinding;

public class DragonHandler
{
    protected final Handler CHARGER = new Handler();
    protected final GameFlux res;
    protected final Dragon dragon;

    public DragonHandler setBinder(@Nullable ListItemEventsBinding binder) {
        this.binder = binder;
        return this;
    }

    @Nullable @org.jetbrains.annotations.Nullable
    protected  ListItemEventsBinding binder;

    public DragonHandler(GameFlux res, Dragon dragon) {
        this.res = res;
        this.dragon = dragon;
    }

    private Runnable charger = () -> charge();

    void charge()
    {
        if(binder != null)binder.dragonCPower.setProgress(this.dragon.power);
        //LOG.v(this.getClass().getSimpleName(), "START CHARGING");
        if (dragon.power < 10000) {
            if(res.navController.getCurrentDestination().getId()!= R.id.prePlayer && res.navController.getCurrentDestination().getId()!=R.id.damageStepFragment) this.dragon.power+=4;
          //  LOG.w("CHARGING: ", dragon.getNameAsString()+"{power = "+dragon.power +"}");
            CHARGER.postDelayed(charger, 100);
        } else CHARGER.removeCallbacks(charger);
    }
    public void init()
    {
        CHARGER.post(charger);
    }
}