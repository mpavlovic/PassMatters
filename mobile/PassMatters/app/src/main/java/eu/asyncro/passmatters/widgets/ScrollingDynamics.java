package eu.asyncro.passmatters.widgets;


public class ScrollingDynamics
{
    public final static String TAG = ScrollingDynamics.class.getName();
    
    public final static int MINIMUM_DELTA = 1;
    
    //Number of animation steeps
    private final static int NUMBER_OF_STEEPS = 10;
    
    private int currentPosition;
    private int endPosition;
    private int viewToCenterPosition;
    
    //length of single animation iteration.
    private int delta;
    //maximum distance to final position which may be considered as maximum length of last iteration steep
    // this distance must be a bit longer than delta in order to avoid overscrolling.
    private float proximityDistance;
    private ThreeDListView parent;
    
    public ScrollingDynamics(final ThreeDListView theParent)
    {
        this.parent = theParent;
    }
    
    public void resetParameters(final int theCurrentPosition,final int theEndPosiiton,final int theViewToCenterPosition)
    {
        this.currentPosition = theCurrentPosition;
        this.endPosition = theEndPosiiton;
        this.viewToCenterPosition = theViewToCenterPosition; 
        this.delta = (endPosition - currentPosition) / NUMBER_OF_STEEPS;
       
        if(this.delta == 0)
        {
            if(endPosition > currentPosition)
                this.delta = MINIMUM_DELTA;
            else
                this.delta = - MINIMUM_DELTA;
        }     
        
        this.proximityDistance =  Math.abs((float)(delta * 1.1));
    }
    
    public boolean update()
    {
        this.currentPosition += delta;        
        //Check if we reached desired position.
        if(Math.abs(this.currentPosition - this.endPosition)< this.proximityDistance)
        {
            this.parent.setSelectionFromTop(viewToCenterPosition, endPosition);
            return false;
        }else {
            this.parent.setSelectionFromTop(viewToCenterPosition, currentPosition);
            return true;
        }
    }
}
