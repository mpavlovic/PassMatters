package eu.asyncro.passmatters.presenters;

import android.view.View;
import android.widget.TextView;

import com.dmacan.lightandroid.presenter.LightAdapterItem;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.data.DataAccount;

/**
 * Created by ahuskano on 11/14/2014.
 */
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
}
