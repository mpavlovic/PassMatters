package eu.asyncro.passmatters.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.lightandroid.api.LightResponse;
import com.lightandroid.api.listener.OnDataReadListener;
import com.lightandroid.api.listener.OnErrorListener;

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
 * Fragment class for accounts showing
 */
public class FragmentAccounts extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnDataReadListener, OnAccountSendedListener, OnErrorListener, OnLogOutListener, AdapterView.OnItemClickListener, OnLockListener {

    private ControllerAccounts controllerAccounts;
    private ControllerAccount controllerAccount;
    private ThreeDListAdapter adapter;
    private ThreeDListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DataAccount> accountList;

    /**
     * Method to provide layout
     * @return layout
     */
    @Override
    public int provideLayoutRes() {
        return R.layout.fragment_accounts;
    }

    /**
     * Method to set up fragment
     */
    @Override
    public void main() {
        init();
        swipeRefreshLayout.setRefreshing(true);
        controllerAccounts.getAccounts(ManagerSession.getToken(getActivity().getBaseContext()));
    }

    /**
     * Method to set up fragment
     */
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

    /**
     * Method that will be called when server return data
     * @param response
     */
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
        controllerAccount.dismissDialog();
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
    }

    /**
     * Method used to clear list
     */
    private void clearList() {
        accountList.clear();
    }


    /**
     * Method that will be called when server return a error
     * @param error
     */
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


    /**
     * Method that return menu
     * @return menu
     */
    @Override
    public int getMenuResource() {
        return R.menu.accounts_menu;
    }

    /**
     * Method that will be called when user need to log out from app
     * @param response
     */
    @Override
    public void onLogOut(LightResponse response) {
        ResponseLogOut logOutResponse = (ResponseLogOut) response;
        if (response.getCode() == getResources().getInteger(R.integer.success_code)) {
            onLock();
        }
        controllerLogOut.dismissDialog();
        toastIt(response.getMessage());
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            DataAccount data = (DataAccount) adapter.getItem(i - 1);
            controllerAccount.setAccount(ManagerSession.getToken(getActivity().getBaseContext()), data.getId());
        }
    }

    /**
     * Method that will be called when server return response from account
     * @param response
     */
    @Override
    public void onAccountSended(LightResponse response) {
        ResponseAccount responseAccount = (ResponseAccount) response;
        if (responseAccount.getStatus() == getResources().getInteger(R.integer.error_code))
            controllerLogOut.logOut(ManagerSession.getToken(getActivity().getBaseContext()));
        controllerAccount.dismissDialog();
    }

    /**
     * Method used to set up listview
     * @return listview
     */
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

    /**
     * Method called when user will be redirect to log in screen
     */
    @Override
    public void onLock() {
        ((ActivityMain) getActivity()).getLockReceiver().destroy();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSignIn()).commit();

    }

    /**
     * Method used to enable refreshing
     */
    @Override
    public void refresh() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        controllerAccounts.getAccounts(ManagerSession.getToken(getActivity().getBaseContext()));
    }

    /**
     * Method that will be called when user swipe list
     */
    @Override
    public void onRefresh() {

    }
}
