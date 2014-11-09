package eu.asyncro.passmatters.fragments;

import android.widget.ListView;

import com.dmacan.lightandroid.LightFragment;
import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.api.listener.OnDataReadListener;
import com.dmacan.lightandroid.api.listener.OnErrorListener;
import com.dmacan.lightandroid.presenter.LightAdapter;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerAccounts;
import eu.asyncro.passmatters.data.requests.RequestAccounts;
import retrofit.RetrofitError;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class FragmentAccounts extends LightFragment implements OnDataReadListener, OnErrorListener {

    private ControllerAccounts controllerAccounts;
    private LightAdapter adapter;
    private ListView listView;

    @Override
    public int provideLayoutRes() {
        return R.layout.fragment_accounts;
    }

    @Override
    public void main() {
        init();
        RequestAccounts requestAccounts= new RequestAccounts();
        requestAccounts.setToken("tokenasdjkas");
        controllerAccounts.getAccounts(requestAccounts);


    }

    private void init() {
        controllerAccounts = new ControllerAccounts();
        controllerAccounts.setOnDataReadListener(this);
        controllerAccounts.setOnErrorListener(this);
        listView=(ListView) getView().findViewById(R.id.lvAccounts);
    }

    @Override
    public void onDataRead(LightResponse response) {

        adapter=new LightAdapter(getActivity().getBaseContext());
        listView.setAdapter(adapter);
    }

    @Override
    public void onError(RetrofitError error) {

    }
}
