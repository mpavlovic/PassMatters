package eu.asyncro.passmatters.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.api.listener.OnDataReadListener;
import com.dmacan.lightandroid.api.listener.OnErrorListener;

import java.util.ArrayList;
import java.util.List;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.activities.ActivityMain;
import eu.asyncro.passmatters.adapters.ThreeDListAdapter;
import eu.asyncro.passmatters.controllers.ControllerAccount;
import eu.asyncro.passmatters.controllers.ControllerAccounts;
import eu.asyncro.passmatters.controllers.ControllerLogOut;
import eu.asyncro.passmatters.controllers.ManagerSession;
import eu.asyncro.passmatters.data.DataAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import eu.asyncro.passmatters.data.responses.ResponseLogOut;
import eu.asyncro.passmatters.interfaces.OnAccountSendedListener;
import eu.asyncro.passmatters.interfaces.OnLockListener;
import eu.asyncro.passmatters.interfaces.OnLogOutListener;
import eu.asyncro.passmatters.receivers.LockReceiver;
import eu.asyncro.passmatters.widgets.HighlightedViewContainer;
import eu.asyncro.passmatters.widgets.ThreeDListItemView;
import eu.asyncro.passmatters.widgets.ThreeDListView;
import retrofit.RetrofitError;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class FragmentAccounts extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnDataReadListener, OnAccountSendedListener, OnErrorListener, OnLogOutListener, AdapterView.OnItemClickListener, OnLockListener {

    private ControllerAccounts controllerAccounts;
    private ControllerAccount controllerAccount;
    private ThreeDListAdapter adapter;
    private ThreeDListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DataAccount> accountList;

    @Override
    public int provideLayoutRes() {
        return R.layout.fragment_accounts;
    }

    @Override
    public void main() {
        init();
        swipeRefreshLayout.setRefreshing(true);
        controllerAccounts.getAccounts(ManagerSession.getToken(getActivity().getBaseContext()));
    }

    private void init() {
        accountList = new ArrayList<DataAccount>();
        controllerAccounts = new ControllerAccounts(getActivity());
        controllerAccounts.setOnDataReadListener(this);
        controllerAccounts.setOnErrorListener(this);
        controllerLogOut = new ControllerLogOut(getActivity());
        controllerLogOut.setOnLogOutListener(this);
        controllerAccounts.setOnErrorListener(this);
        controllerAccount = new ControllerAccount(getActivity());
        controllerAccount.setOnAccountSendedListener(this);
        listView = (ThreeDListView) getView().findViewById(R.id.threeDListView);
        adapter = new ThreeDListAdapter(getActivity());
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        ((ActivityMain) getActivity()).setLockReceiver(new LockReceiver(getActivity(), this));
        initThreeDList();
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorScheme(R.color.swipe_color_1, R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onDataRead(LightResponse response) {
        ResponseAccounts accounts = (ResponseAccounts) response;
        clearList();
        if (accounts.getCode() == getResources().getInteger(R.integer.success_code)) {
            for (DataAccount data : accounts.getAccounts())
                accountList.add(data);
            if (adapter.getCount() == 0)
                adapter.setItems(accountList);
            adapter.notifyDataSetChanged();
            if (adapter.getCount() != 0)
                ((LinearLayout) getView().findViewById(R.id.linearLayout1)).setVisibility(View.VISIBLE);
        } else {
            toastIt(accounts.getMessage());
        }
        controllerAccounts.dismissDialog();
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
    }

    private void clearList() {
        accountList.clear();
    }


    @Override
    public void onError(RetrofitError error) {
        controllerAccounts.dismissDialog();
        controllerAccount.dismissDialog();
        controllerLogOut.dismissDialog();
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
            onLock();
        }
        controllerLogOut.dismissDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            DataAccount data = (DataAccount) adapter.getItem(i - 1);
            controllerAccount.setAccount(ManagerSession.getToken(getActivity().getBaseContext()), data.getId());
        }
    }

    @Override
    public void onAccountSended(LightResponse response) {
        ResponseAccount responseAccount = (ResponseAccount) response;
        if (responseAccount.getStatus() == getResources().getInteger(R.integer.error_code))
            controllerLogOut.logOut(ManagerSession.getToken(getActivity().getBaseContext()));
        controllerAccount.dismissDialog();
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

    @Override
    public void onLock() {
        ((ActivityMain) getActivity()).getLockReceiver().destroy();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSignIn()).commit();

    }

    @Override
    public void refresh() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        controllerAccounts.getAccounts(ManagerSession.getToken(getActivity().getBaseContext()));
    }

    @Override
    public void onRefresh() {

    }
}
