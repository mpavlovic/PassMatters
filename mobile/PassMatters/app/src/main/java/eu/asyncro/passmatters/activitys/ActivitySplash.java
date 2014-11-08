package eu.asyncro.passmatters.activitys;

import com.dmacan.lightandroid.LightSplashActivity;

import eu.asyncro.passmatters.R;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class ActivitySplash extends LightSplashActivity {
    @Override
    public int provideLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public int getSplashTime() {
        return 20;
    }

    @Override
    public Class getNextClassActivity() {
        return ActivityMain.class;
    }
}
