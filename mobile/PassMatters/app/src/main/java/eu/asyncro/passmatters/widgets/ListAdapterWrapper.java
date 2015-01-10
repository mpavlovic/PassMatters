package eu.asyncro.passmatters.widgets;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * We need wrapper class in order to track current selection after list refresh.
 * @author Lemberg Solutions
 *
 */
public class ListAdapterWrapper implements ListAdapter
{
    public final static String TAG = ListAdapterWrapper.class.getName();
    
    private ListAdapter adapter;
    private ThreeDListView parent;

    ListAdapterWrapper(final ThreeDListView theParent)
    {
        this.parent = theParent;
    }
       
    public ListAdapter getAdapter()
    {
        return adapter;
    }

    public void setAdapter(final ListAdapter adapter)
    {
        this.adapter = adapter;       
    }

    @Override
    public int getCount()
    {
       if(this.adapter!= null)
       {
           return this.adapter.getCount();
       } else {
           return 0;
       }
    }

    @Override
    public Object getItem(final int position)
    {
        if(this.adapter!= null)
        {
            return this.adapter.getItem(position);
        } else {
            return null;
        }       
    }

    @Override
    public long getItemId(final int position)
    {
        if(this.adapter!= null)
        {
            return this.adapter.getItemId(position);
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(final int position)
    {
        if(this.adapter!= null)
        {
            return this.adapter.getItemViewType(position);
        } else {
            return 0;
        }
    }

    @Override
    public View getView(final int position,final  View convertView, final ViewGroup parent)
    {    	
        if(this.adapter != null)
        {
        	//We're tracking changes in view list here in order to refresh highlighted view
            View view = this.adapter.getView(position, convertView, parent);           
            if(position == this.parent.getLastHighlightedItemPosition() -1)//-1 because adapter children are enumerated from 1 to ..
            {
                HighlightedViewContainer container = this.parent.getHighlightViewContainer();
                if(container != null)
                {                	
                    container.setView(view);
                }
            }
            return view;
        } else {
            return null;
        }
    }

    @Override
    public int getViewTypeCount()
    {
        if(this.adapter!= null)
        {
            return this.adapter.getViewTypeCount();
        } else {
            return 0;
        }
    }

    @Override
    public boolean hasStableIds()
    {
        if(this.adapter!= null)
        {
            return this.adapter.hasStableIds();
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty()
    {
        if(this.adapter!= null)
        {
            return this.adapter.isEmpty();
        } else {
            return false;
        }
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer)
    {
        if(this.adapter!= null)
        {
            this.adapter.registerDataSetObserver(observer);
        } 
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer)
    {
        if(this.adapter!= null)
        {
            this.adapter.unregisterDataSetObserver(observer);
        } 
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        if(this.adapter!= null)
        {
            return this.adapter.areAllItemsEnabled();
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        if(this.adapter!= null)
        {
            return this.adapter.isEnabled(arg0);
        } else {
            return false;
        }
    }

}
