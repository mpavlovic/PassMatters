package eu.asyncro.passmatters.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.api.listener.OnDataReadListener;
import com.dmacan.lightandroid.api.listener.OnErrorListener;

import java.util.ArrayList;
import java.util.List;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerAccount;
import eu.asyncro.passmatters.controllers.ControllerAccounts;
import eu.asyncro.passmatters.controllers.ControllerLogOut;
import eu.asyncro.passmatters.controllers.ManagerSession;
import eu.asyncro.passmatters.adapters.ThreeDListAdapter;
import eu.asyncro.passmatters.widgets.ThreeDListItemView;
import eu.asyncro.passmatters.data.DataAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import eu.asyncro.passmatters.data.responses.ResponseLogOut;
import eu.asyncro.passmatters.interfaces.OnAccountSendedListener;
import eu.asyncro.passmatters.interfaces.OnLogOutListener;
import eu.asyncro.passmatters.widgets.HighlightedViewContainer;
import eu.asyncro.passmatters.widgets.ThreeDListView;
import retrofit.RetrofitError;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class FragmentAccounts extends BaseFragment implements OnDataReadListener, OnAccountSendedListener, OnErrorListener, OnLogOutListener, AdapterView.OnItemClickListener {

    private ControllerAccounts controllerAccounts;
    private ControllerAccount controllerAccount;
    private ThreeDListAdapter adapter;
    private ThreeDListView listView;

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
        controllerLogOut = new ControllerLogOut();
        controllerLogOut.setOnLogOutListener(this);
        controllerAccounts.setOnErrorListener(this);
        controllerAccount = new ControllerAccount();
        controllerAccount.setOnAccountSendedListener(this);
        listView = (ThreeDListView) getView().findViewById(R.id.threeDListView);
        adapter = new ThreeDListAdapter(getActivity());
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        initThreeDList();
    }

    @Override
    public void onDataRead(LightResponse response) {
        ResponseAccounts accounts = (ResponseAccounts) response;
        if (accounts.getCode() == getResources().getInteger(R.integer.success_code)) {
            List<DataAccount> accountList = new ArrayList<DataAccount>();
            for (DataAccount data : accounts.getAccounts())
                accountList.add(data);
            adapter.setItems(accountList);
            adapter.notifyDataSetChanged();
        } else {
            toastIt(accounts.getMessage());
        }
    }

    @Override
    public void onError(RetrofitError error) {

    }

    private void fillAdapter(DataAccount[] accounts) {
        // for (DataAccount account : accounts)
        // adapter.addItem(new PresenterAccount(account));
    }


    @Override
    public int getMenuResource() {
        return R.menu.accounts_menu;
    }

    @Override
    public void onLogOut(LightResponse response) {
        ResponseLogOut logOutResponse = (ResponseLogOut) response;
        if (response.getCode() == getResources().getInteger(R.integer.success_code)) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSignIn()).commit();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        DataAccount data = (DataAccount) adapter.getItem(i-1);
        controllerAccount.setAccount(ManagerSession.getToken(getActivity().getBaseContext()), data.getId());
    }

    @Override
    public void onAccountSended(LightResponse response) {
        ResponseAccount responseAccount = (ResponseAccount) response;
        if (responseAccount.getStatus() == getResources().getInteger(R.integer.error_code))
            controllerLogOut.logOut(ManagerSession.getToken(getActivity().getBaseContext()));
    }

    private ThreeDListView initThreeDList() {
        listView.setHighlightViewContainer(new HighlightedViewContainer() {
            @Override
            public void performHighlightAction(final View theView) {
                ThreeDListItemView selectedView = (ThreeDListItemView) theView;
                if (selectedView != null) {
                    selectedView.setChecked(true);
                }
            }

            @Override
            public void performDehighlightAction(final View theView) {
                ThreeDListItemView deSelectedView = (ThreeDListItemView) theView;
                if (deSelectedView != null) {
                    deSelectedView.setChecked(false);
                }
            }
        });
        return listView;
    }
}
