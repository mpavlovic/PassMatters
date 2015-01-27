package eu.asyncro.passmatters.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import com.lightandroid.LightActivity;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.fragments.FragmentSignIn;
import eu.asyncro.passmatters.receivers.LockReceiver;

/**
 * Main activity, holder of application
 */
public class ActivityMain extends LightActivity {

    private LockReceiver lockReceiver;

    private long oldTime = SystemClock.elapsedRealtime();
    private long newTime = SystemClock.elapsedRealtime();
    private long DELAY = 500000;

    /**
     * Method used to provide layout
     *
     * @return layout
     */
    @Override
    public int provideLayoutRes() {
        return R.layout.activity_main;
    }

    /**
     * Method used to set up activity
     */
    @Override
    public void main() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new FragmentSignIn(),FragmentSignIn.class.getSimpleName()).commit();
    }

    /**
     * Method used to records user interaction
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        newTime = SystemClock.elapsedRealtime();
        if (newTime - oldTime >= DELAY) {
            sendAction();
            oldTime = newTime;
        } else
            oldTime = newTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lockReceiver != null) {
            lockReceiver.destroy();
        }

    }

    public LockReceiver getLockReceiver() {
        return lockReceiver;
    }

    public void setLockReceiver(LockReceiver lockReceiver) {
        this.lockReceiver = lockReceiver;

    }

    /**
     * Method used to send action to broadcast receiver
     */
    private void sendAction() {
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(
                new Intent(LockReceiver.KEY));
    }
}
