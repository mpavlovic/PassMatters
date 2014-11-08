package eu.asyncro.passmatters.activitys;

import com.dmacan.lightandroid.LightActivity;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.fragments.FragmentSignIn;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class ActivityMain extends LightActivity {
    @Override
    public int provideLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void main() {
        getSupportFragmentManager().beginTransaction().add(R.id.container,new FragmentSignIn()).commit();
    }
}
