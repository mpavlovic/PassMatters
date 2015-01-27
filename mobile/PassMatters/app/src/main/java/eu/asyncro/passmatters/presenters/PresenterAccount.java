package eu.asyncro.passmatters.presenters;

import android.view.View;
import android.widget.TextView;

import com.lightandroid.presenter.LightAdapterItem;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.data.DataAccount;

/**
 * Deprecated presenter for accounts
 */
@Deprecated
public class PresenterAccount implements LightAdapterItem {

    private DataAccount account;

    public PresenterAccount(DataAccount account) {
        this.account = account;
    }

    @Override
    public void display(View view, int position) {
        ((TextView) view.findViewById(R.id.tvAccountName)).setText(account.getName());
    }

    @Override
    public int provideItemLayoutRes() {
        return R.layout.account_list_item;
    }

    public DataAccount getAccount() {
        return account;
    }

    public void setAccount(DataAccount account) {
        this.account = account;
    }
}
