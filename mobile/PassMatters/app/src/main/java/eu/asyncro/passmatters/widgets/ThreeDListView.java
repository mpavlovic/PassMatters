package eu.asyncro.passmatters.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ThreeDListView extends ListView
{
	public static final String TAG = ThreeDListView.class.getName();

	private static class StubView extends View
	{
		public final static String TAG = StubView.class.getName();

		public StubView(final Context context)
		{
			super(context);
			this.setTag(TAG);
		}
	}

	private final static int INITIAL_DISTANCE_FROM_CENTER = -1;

	public final static int DEFAULT_CENTRAL_ELEMENT_PADDING = 17;
	public final static int DEFAULT_BORDER_ELEMENT_PADDING = -30;

	private final static int CENTRAL_ALPHA = 255;

	private final static int BORDER_ALPHA = 0;

	private final static int SCROLLING_TICK_TIME = 16;

	private ScrollingDynamics dynamics;

	//We need to replace default adapter with wrapper in order to handle item selection
	private ListAdapterWrapper listAdapterWrapper;

	private int highlightedPosition;

	private int highlightedItemCurrentPaddingTop;

	private View childToSelect;

	//We use this stub views in order to allow first and last list items to be scrolled to center of ListView
	
	private StubView footerView;
	private StubView headerView;

	private boolean onAutoScroll;

	private boolean onNativeScroll;

	private boolean useChildDrawingCache;

	private float minDstanceFromCenter;

	private Paint paint;

	private HighlightedViewContainer highlightContainer;

	private OnScrollListener scrollListener;

	// This is transparency of element which is positioned near top or bottom
	// borders.
	private int borderAlpha;

	// This is transparency of central element
	private int centralAlpha;

	// This is left padding of central element
	private int centralElementPadding;

	// This is left padding of element which is positioned near top or bottom
	private int borderElementPadding;

	public ThreeDListView(final Context context)
	{
		super(context);
		initView(context);
	}

	public ThreeDListView(final Context context, final AttributeSet theSet)
	{
		super(context, theSet);
		initView(context);
	}

	/**
	 * This feature was implemented in SDK 9 and produces orange fading on some
	 * devices we use reflection to disable it.
	 */
	public void disableOverScroll()
	{
		int sdkVersion = Build.VERSION.SDK_INT;

		if (sdkVersion >= 9)
		{
			try
			{
				Method m = this.getClass().getMethod("setOverScrollMode", new Class[] { int.class });
				m.invoke(this, Integer.valueOf(2));
			} catch (SecurityException e2)
			{
				e2.printStackTrace();
			} catch (NoSuchMethodException e2)
			{
				e2.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean drawChild(final Canvas canvas, final View child, final long drawingTime)
	{
		// We don't need to draw stubs.
		Object tag = child.getTag();
		if (tag != null)
		{
			if (tag == StubView.TAG)
			{
				return false;
			}
		}

		final int childWidth = child.getWidth();
		final int childHeight = child.getHeight();
		final int centerY = childHeight / 2;
		final int top = child.getTop();
		final int left = child.getLeft();
		final float halfHeight = getHeight() / 2f;
		final float distFromCenter = Math.abs((top + centerY - halfHeight) / halfHeight);
		final float scaleCoef = getEllipticValue(distFromCenter);

		validateInitialSlection(child, halfHeight, centerY);

		if (distFromCenter < this.minDstanceFromCenter || (this.minDstanceFromCenter == INITIAL_DISTANCE_FROM_CENTER))
		{
			this.minDstanceFromCenter = distFromCenter;
			this.childToSelect = child;
			this.highlightedPosition = this.getPositionForView(child);
			this.highlightedItemCurrentPaddingTop = top;
		}

		// Trying to get bitmap from drawing cache. Warning! Default view's
		// drawing cache produces bitmap WITHOUT ALPHA channel, so transparent
		// regions may appear black. in that case you may override getDrawingCache method of your view.
		Bitmap bitmap = null;
		if (useChildDrawingCache)
		{
			bitmap = child.getDrawingCache();
		}
		if (bitmap == null)
		{
			bitmap = Bitmap.createBitmap(childWidth, childHeight, Bitmap.Config.ARGB_8888);
			Canvas view = new Canvas(bitmap);
			child.draw(view);
		}

		//This option smooths view representation, but it decreases performance.
		paint.setAntiAlias(true);
		
		paint.setAlpha((int) ((this.centralAlpha - this.borderAlpha) * scaleCoef + this.borderAlpha));
		canvas.drawBitmap(bitmap,null,
				getChildBounds(top, (int) (left - ((1 - scaleCoef) * (this.centralElementPadding - this.borderElementPadding))), childWidth, childHeight,
						scaleCoef), paint);
		return false;
	}

	private Rect getChildBounds(final int theTop, final int theLeft, final int theWidth, final int theHeight, final float theScaleF)
	{
		final int scaledWidth = (int) (theWidth * theScaleF);
		final int scaledHeight = (int) (theHeight * theScaleF);
		final int paddingTop = (theHeight - scaledHeight) / 2;
		Rect bounds = new Rect(theLeft, theTop + paddingTop, theLeft + scaledWidth, theTop + paddingTop + scaledHeight);
		return bounds;
	}

	@Override
	public ListAdapter getAdapter()
	{
		if (this.listAdapterWrapper != null)
		{
			return this.listAdapterWrapper.getAdapter();
		} else
		{
			return null;
		}
	}

	public HighlightedViewContainer getHighlightViewContainer()
	{
		return this.highlightContainer;
	}

	public int getLastHighlightedItemPosition()
	{
		return this.highlightedPosition;
	}

	@Override
	public void onDraw(final Canvas theCanvas)
	{
		this.minDstanceFromCenter = INITIAL_DISTANCE_FROM_CENTER;
		super.onDraw(theCanvas);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent theEvent)
	{
		return true;
	}

	@Override
	public void onSizeChanged(final int theW, final int theH, final int theOldW, final int theOldH)
	{
		super.onSizeChanged(theW, theH, theOldW, theOldH);
		applyNewSize();
	}

	@Override
	public boolean onTouchEvent(final MotionEvent theEvent)
	{
		boolean result = super.onTouchEvent(theEvent);
		if (theEvent.getAction() == MotionEvent.ACTION_DOWN)
		{
			this.onAutoScroll = false;
		}

		if (theEvent.getAction() == MotionEvent.ACTION_UP)
		{
			if (!this.onNativeScroll)
			{
				this.lockPosition(this.childToSelect);
			}
		}
		return result;
	}

	@Override
	public void setAdapter(final ListAdapter theAdapter)
	{
		this.listAdapterWrapper.setAdapter(theAdapter);
		if (theAdapter != null)
		{
			super.setAdapter(this.listAdapterWrapper);
		} else
		{
			super.setAdapter(null);
		}
	}

	public void setHighlightViewContainer(final HighlightedViewContainer theContainer)
	{
		if (theContainer != null)
		{
			this.highlightContainer = theContainer;
		} else
		{
			setDefaultHighlightContainer();
		}
	}

	@Override
	public void setOnScrollListener(final OnScrollListener theListener)
	{
		this.scrollListener = theListener;
	}

	/**
	 * Enabling of drawing cache will increase performance, but cached views
	 * should be without transparency
	 * 
	 * @param theUse
	 *            if true - usage of drawing cache will be enabled
	 */
	public void useCHildDrawingCache(final boolean theUse)
	{
		this.useChildDrawingCache = theUse;
	}

	private void applyNewSize()
	{
		//We need to update stubs height
		
		int stubsHeight = this.getHeight() / 2;	
		footerView.setMinimumHeight(stubsHeight);
		headerView.setMinimumHeight(stubsHeight);
	}

	private static float getEllipticValue(final float theY)
	{
		float result = (float) Math.sqrt(1 - theY);
		if (result > 1)
		{
			return 1;
		} else
		{
			return result;
		}
	}

	private void initListAdapterWrapper()
	{
		this.listAdapterWrapper = new ListAdapterWrapper(this);
	}

	private void initOverscrollingStubs(final Context theContext)
	{
		footerView = new StubView(theContext);
		this.addFooterView(footerView);
		headerView = new StubView(theContext);
		this.addHeaderView(headerView);
	}

	private void initView(final Context theContext)
	{
		paint = new Paint();
		dynamics = new ScrollingDynamics(this);
		this.useChildDrawingCache = true;
		this.onNativeScroll = false;
		this.setDividerHeight(0);
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.setScrollListener();
		this.initOverscrollingStubs(theContext);
		this.setDefaultHighlightContainer();
		this.initListAdapterWrapper();
		this.disableOverScroll();
		this.setCentralElementPadding(DEFAULT_CENTRAL_ELEMENT_PADDING);
		this.setBorderAlpha(BORDER_ALPHA);
		this.setCentralAlpha(CENTRAL_ALPHA);
		this.setBorderElementPadding(DEFAULT_BORDER_ELEMENT_PADDING);
	}
		
	/**
	 * This method scrolls list to center specified child 
	 * @param theChildtoSelect
	 */
	private void lockPosition(final View theChildtoSelect)
	{
		if ((theChildtoSelect != null) && (!this.onAutoScroll) && (this.minDstanceFromCenter != INITIAL_DISTANCE_FROM_CENTER))
		{
			this.onAutoScroll = true;
			this.dynamics.resetParameters(this.highlightedItemCurrentPaddingTop, this.getHeight() / 2 - theChildtoSelect.getHeight() / 2,
					this.highlightedPosition);
			this.post(new Runnable()
			{
				@Override
				public void run()
				{
					if (ThreeDListView.this.onAutoScroll)
					{
						if (ThreeDListView.this.dynamics.update())
						{
							postDelayed(this, SCROLLING_TICK_TIME);
						} else
						{
							lockSelection(childToSelect);
						}
					}
				}
			});
		}
	}

	private void lockSelection(final View theChildtoSelect)
	{
		if (this.highlightContainer != null)
		{
			this.highlightContainer.setView(theChildtoSelect);
		}
		this.onAutoScroll = false;
	}

	private void setDefaultHighlightContainer()
	{
		this.setHighlightViewContainer(new HighlightedViewContainer()
		{
			@Override
			public void performDehighlightAction(View theView)
			{
			}

			@Override
			public void performHighlightAction(View theView)
			{
			}
		});
	}
	
	private void setScrollListener()
	{
		//We need wrapper over custom scroll listener in order to handle scroll events
		super.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				if (ThreeDListView.this.scrollListener != null)
				{
					ThreeDListView.this.scrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				}
			}

			@Override
			public void onScrollStateChanged(final AbsListView view, final int scrollState)
			{
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					ThreeDListView.this.onNativeScroll = false;
					lockPosition(ThreeDListView.this.childToSelect);
				}

				if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
				{
					ThreeDListView.this.onNativeScroll = true;
					if (ThreeDListView.this.highlightContainer != null)
					{
						ThreeDListView.this.highlightContainer.setView(null);
					}
				}

				if (ThreeDListView.this.scrollListener != null)
				{
					ThreeDListView.this.scrollListener.onScrollStateChanged(view, scrollState);
				}
			}
		});
	}

	/**
	 * This method is used to perform initial selection
	 * @param child
	 * @param halfHeight
	 * @param centerY
	 */
	private void validateInitialSlection(final View child, final float halfHeight, final float centerY)
	{
		if (this.childToSelect == null)
		{
			this.minDstanceFromCenter = 0;
			this.childToSelect = child;
			this.setSelectionFromTop(1, (int) (halfHeight - centerY));
			this.lockSelection(child);
		}
	}

	/**
	 * You should call superclass method, if overridden
	 * 
	 * @param scrollX
	 * @param scrollY
	 * @param clampedX
	 * @param clampedY
	 */
	protected void onOverScrolled(final int scrollX, final int scrollY, final boolean clampedX, final boolean clampedY)
	{
		//We need to select view after overscroll event is performed
		lockPosition(this.childToSelect);
	}

	/**
	 * @return transparency of element which is positioned near top or bottom
	 *         borders [0,255].
	 */
	public int getBorderAlpha()
	{
		return borderAlpha;
	}

	/**
	 * @param borderAlpha
	 *            transparency of element which is positioned near top or bottom
	 *            borders [0,255].
	 */
	public void setBorderAlpha(int borderAlpha)
	{
		this.borderAlpha = borderAlpha;
	}

	/**
	 * @return transparency of central element
	 */
	public int getCentralAlpha()
	{
		return centralAlpha;
	}

	/**
	 * @param centralAlpha
	 *            transparency of central element
	 */
	public void setCentralAlpha(int centralAlpha)
	{
		this.centralAlpha = centralAlpha;
	}

	/**
	 * @return left padding of central element
	 */
	public int getCentralElementPadding()
	{
		return centralElementPadding;
	}

	/**
	 * @param centralElementPadding
	 *            left padding of central element
	 */
	public void setCentralElementPadding(int centralElementPadding)
	{
		this.centralElementPadding = centralElementPadding;
	}

	/**
	 * @return left padding of element which is positioned near top or bottom ,
	 *         may be negative
	 */
	public int getBorderElementPadding()
	{
		return borderElementPadding;
	}

	/**
	 * @param borderElementPadding
	 *            left padding of element which is positioned near top or bottom
	 *            , may be negative
	 */
	public void setBorderElementPadding(int borderElementPadding)
	{
		this.borderElementPadding = borderElementPadding;
	}

}
