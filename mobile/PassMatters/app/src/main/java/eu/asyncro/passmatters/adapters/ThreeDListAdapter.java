package eu.asyncro.passmatters.adapters;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.data.DataAccount;
import eu.asyncro.passmatters.widgets.ThreeDListItemView;

/**
 * We can use usual adapter with our ThreeDListView
 * @author Lemberg Solutions
 *
 */
public class ThreeDListAdapter extends BaseAdapter
{
    public final static String TAG =ThreeDListAdapter.class.getName();
    
    private List<DataAccount> items;
    private Context context;
    
    public ThreeDListAdapter(final Context theContext)
    {
        super();
        this.items = new LinkedList<DataAccount>();
        this.context = theContext;
    }
    
    public void setItems(final List<DataAccount> theItems)
    {
        items.clear();
        items.addAll(theItems);
    }            
    
    @Override
    public int getCount()
    {        
        return items.size();
    }

    @Override
    public Object getItem(final int arg0)
    {       
        return items.get(arg0);
    }

    @Override
    public long getItemId(final int arg0)
    {       
        return 0;
    }

    @Override
    public View getView(final int thePosition,final View theConvertView,final ViewGroup arg2)
    {
        ThreeDListItemView viewItem;
        if(theConvertView!=null)
        {
            viewItem = (ThreeDListItemView)theConvertView;           
        }else{
            viewItem = new ThreeDListItemView(context);
        }
        
        DataAccount data = (DataAccount)this.getItem(thePosition);
        
        viewItem.setText(data.getName());
        
        viewItem.setImage(context.getResources().getDrawable(R.drawable.ic_icon));
        viewItem.setId(thePosition);       
        viewItem.validateState();
        return viewItem;
    }   
}
