package eu.asyncro.passmatters.fragments;

import android.widget.ListView;

import com.dmacan.lightandroid.LightFragment;
import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.api.listener.OnDataReadListener;
import com.dmacan.lightandroid.api.listener.OnErrorListener;
import com.dmacan.lightandroid.presenter.LightAdapter;

import java.util.ArrayList;
import java.util.List;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerAccounts;
import eu.asyncro.passmatters.controllers.ManagerSession;
import eu.asyncro.passmatters.data.DataAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import eu.asyncro.passmatters.presenters.PresenterAccount;
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
        controllerAccounts.getAccounts(ManagerSession.getToken(getActivity().getBaseContext()));
    }

    private void init() {
        controllerAccounts = new ControllerAccounts();
        controllerAccounts.setOnDataReadListener(this);
        controllerAccounts.setOnErrorListener(this);
        listView = (ListView) getView().findViewById(R.id.lvAccounts);
    }

    @Override
    public void onDataRead(LightResponse response) {
        ResponseAccounts accounts = (ResponseAccounts) response;
        if (accounts.getCode() == getResources().getInteger(R.integer.success_code)) {
            List<DataAccount> accountList = new ArrayList<DataAccount>();
            for (DataAccount data : accounts.getAccounts()) {
                accountList.add(data);
            }
            adapter = new LightAdapter(getActivity().getBaseContext());
            fillAdapter(accounts.getAccounts());
            listView.setAdapter(adapter);
        } else {
            toastIt(accounts.getMessage());
        }
    }

    @Override
    public void onError(RetrofitError error) {

    }

    private void fillAdapter(DataAccount[] accounts) {
        for (DataAccount account : accounts)
            adapter.addItem(new PresenterAccount(account));
    }
}
