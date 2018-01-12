package me.jerryhanks.sliderview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

import java.util.Timer;

/**
 * @author Jerry Hanks on 1/12/18.
 */

public class Slider extends AppCompatSeekBar {
    private Drawable thumb;
    private static final String TAG = "Slider";

    public Slider(Context context) {
        super(context);
        init(null, 0);
    }

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Slider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attr, int defStylesAttr) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //check for two events DOWN and UP
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getThumb().getBounds().contains((int) event.getX(), (int) event.getY())) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return super.onTouchEvent(event);
            }
            return false;

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //user just stopped swiping
            Log.d(TAG, "Progress: " + getProgress());
            getParent().requestDisallowInterceptTouchEvent(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setProgress(50, true);
            } else {
                setProgress(50);
            }
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void setThumb(Drawable thumb) {
        this.thumb = thumb;
        super.setThumb(thumb);

    }

    @Override
    public Drawable getThumb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return super.getThumb();
        } else {
            return thumb;
        }
    }


}
