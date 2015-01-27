package eu.asyncro.passmatters.activities;

import com.lightandroid.LightSplashActivity;

import eu.asyncro.passmatters.R;

/**
 * Splash activity
 */
public class ActivitySplash extends LightSplashActivity {
    /**
     * Method used to provide layout
     * @return layout
     */
    @Override
    public int provideLayoutRes() {
        return R.layout.activity_splash;
    }

    /**
     * Method used to provide splash time in miliseconds
     * @return
     */
    @Override
    public int getSplashTime() {
        return 20;
    }

    /**
     * Method used to provide next activity
     * @return class
     */
    @Override
    public Class getNextClassActivity() {
        return ActivityMain.class;
    }
}
