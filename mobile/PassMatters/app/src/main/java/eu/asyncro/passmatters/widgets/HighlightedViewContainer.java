package eu.asyncro.passmatters.widgets;

import android.view.View;

/**
 * We may set instance of this class to ThreeDListView in order to perform changes to highlighted views.
 * @author Lemberg Solutions
 *
 */
public abstract class HighlightedViewContainer
{
    private View view;
    
    protected final void setView(final View theView)
    {    	
        performDehighlightAction(this.view);
        this.view = theView;
        performHighlightAction(this.view);
    }
    
    public View getHighlightedView()
    {
        return this.view;
    }
    
    abstract public void performHighlightAction(View theView);    
    
    abstract public void performDehighlightAction(View theView);
    
}
