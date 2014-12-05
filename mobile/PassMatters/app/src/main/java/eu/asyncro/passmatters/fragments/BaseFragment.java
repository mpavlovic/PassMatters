package eu.asyncro.passmatters.fragments;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dmacan.lightandroid.LightFragment;

import eu.asyncro.passmatters.R;

/**
 * Created by ahuskano on 12/5/2014.
 */
public abstract class BaseFragment extends LightFragment {

    public abstract int getMenuResource();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getMenuResource() != 0) inflater.inflate(getMenuResource(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSignIn()).commit();
                break;
        }
        return true;
    }
}
