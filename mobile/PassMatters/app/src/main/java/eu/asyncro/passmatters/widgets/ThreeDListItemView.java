package eu.asyncro.passmatters.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.asyncro.passmatters.R;


/**
 * This class is used to display ThreeDListView item. ThreeDListView may use any
 * view you want, but as soon as we use drawing cache to increase performance,
 * transparent regions may appear black on views, so it's recommended to
 * override "getDrawingCache" method , in case your view includes transparent
 * regions. For this purpose you should override:
 * - setDrawingCacheEnabled
 * - isDrawingCacheEnabled
 * - getDrawingCache
 * Also don't forget to invalidate drawing cache after data set change.
 *
 * @author Lemberg Solutions
 */
public class ThreeDListItemView extends LinearLayout {
    public final static String TAG = ThreeDListView.class.getName();

    private final static float DIP_FACTOR = Resources.getSystem().getDisplayMetrics().density;

    private final static int VIEW_PADDING_LEFT = 17;

    private final static int IMAGE_WIDTH = 176;
    private final static float IMAGE_HEIGHT = 100f;

    private final static float IMAGE_PADDING_X = 10.7f;
    private final static float IMAGE_PADDING_Y = 7.3f;

    private final static float TEXT_SIZE = 18;
    private final static float TEXT_SHADOW_RADIUS = 0.6f;
    private final static int TEXT_COLOR = Color.WHITE;
    private final static int TEXT_SHADOW_COLOR = Color.BLACK;
    private final static int TEXT_PADDING_LEFT = 15;
    private final static float TEXT_VIEW_HEIGHT = 86.7f;

    private ImageView image;
    private TextView text;
    private LinearLayout textLayout;

    private Drawable selectedBG;
    private Drawable deselectedBG;

    //We use this flag in order to define if view was highlighted in ThreeDListView
    private boolean checked;

    //This fields are responsible for containing drawing cache data
    private Paint cachePaint;
    private Bitmap drawingCache;
    private boolean useDrawingCache;

    public ThreeDListItemView(final Context context) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.selectedBG = new ColorDrawable(Color.TRANSPARENT);
        this.deselectedBG = this.getResources().getDrawable(R.drawable.fading_bg);
        this.setBackgroundDrawable(deselectedBG);
        addImageView();
        addTextLayout();
        this.setPadding((int) (VIEW_PADDING_LEFT * DIP_FACTOR), 0, 0, 0);
        this.checked = false;
        this.cachePaint = new Paint();
        this.useDrawingCache = true;
    }

    private void addImageView() {
        this.image = new ImageView(this.getContext());
        this.image.setScaleType(ScaleType.FIT_CENTER);
        this.image.setPadding((int) (IMAGE_PADDING_X * DIP_FACTOR), (int) (IMAGE_PADDING_Y * DIP_FACTOR), (int) (IMAGE_PADDING_X * DIP_FACTOR),
                (int) (IMAGE_PADDING_Y * DIP_FACTOR));
        LayoutParams layP = new LayoutParams((int) (IMAGE_WIDTH * DIP_FACTOR), (int) (IMAGE_HEIGHT * DIP_FACTOR));
        this.image.setLayoutParams(layP);
        this.addView(image);
    }

    private void setTextView(final ViewGroup theParent) {
        this.text = new TextView(getContext());
        this.text.setTextSize(TEXT_SIZE);
        this.text.setPadding((int) (TEXT_PADDING_LEFT * DIP_FACTOR), 0, 0, 0);
        this.text.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        this.text.setTextColor(TEXT_COLOR);
        this.text.setMaxLines(2);
        float textShadowRadius = DIP_FACTOR * TEXT_SHADOW_RADIUS;
        this.text.setShadowLayer(textShadowRadius, 0, -textShadowRadius, TEXT_SHADOW_COLOR);
        this.text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        theParent.addView(this.text);
    }

    private void addTextLayout() {
        textLayout = new LinearLayout(getContext());
        textLayout.setOrientation(LinearLayout.HORIZONTAL);
        textLayout.setGravity(Gravity.CENTER_VERTICAL);
        textLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, (int) (TEXT_VIEW_HEIGHT * DIP_FACTOR)));
        this.addView(textLayout);

        setTextView(textLayout);
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(final boolean theChecked) {
        if (theChecked != this.isChecked()) {
            this.checked = theChecked;
            validateState();
            this.invalidateCache();
        }
    }

    public void validateState() {
        if (this.isChecked()) {
            this.setBackgroundDrawable(selectedBG);
        } else {
            this.setBackgroundDrawable(deselectedBG);
        }
    }

    public void setText(final String theText) {
        this.text.setText(theText);
        this.invalidateCache();
    }

    public void setText(final Spannable theText) {
        this.text.setText(theText);
        this.invalidateCache();
    }

    public void setImage(final int theRes) {
        this.image.setImageResource(theRes);
    }

    @Override
    public void onDraw(final Canvas theCanvas) {
        // Here we're storing drawing cache
        if (this.isDrawingCacheEnabled()) {
            if (this.drawingCache == null) {
                this.drawingCache = Bitmap.createBitmap(theCanvas.getWidth(), theCanvas.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas view = new Canvas(this.drawingCache);
                super.draw(view);
            }
            theCanvas.drawBitmap(drawingCache, 0, 0, cachePaint);
        } else {
            super.onDraw(theCanvas);
        }
    }

    @Override
    public void setDrawingCacheEnabled(final boolean theUse) {
        this.useDrawingCache = theUse;
    }

    @Override
    public boolean isDrawingCacheEnabled() {
        return this.useDrawingCache;
    }

    @Override
    public Bitmap getDrawingCache() {
        return this.drawingCache;
    }

    public void invalidateCache() {
        this.drawingCache = null;
    }

    public void setImage(final Drawable selectedImage) {
        this.image.setImageDrawable(selectedImage);
        this.invalidateCache();
    }
}
