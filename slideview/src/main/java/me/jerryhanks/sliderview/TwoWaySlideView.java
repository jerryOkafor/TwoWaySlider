package me.jerryhanks.sliderview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Jerry Hanks on 1/12/18.
 */

public class TwoWaySlideView extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "TwoWaySlideView";

    private Slider slider;
    private TextView failTv, successTv;
    private OnSlideCallback slideListener;

    public TwoWaySlideView(Context context) {
        super(context);
        init(null, 0);
    }

    public TwoWaySlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TwoWaySlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attr, int defStylesAttr) {
        inflate(getContext(), R.layout.two_way_slide_veiw, this);
        setPadding(10, 10, 10, 10);
        setViewBackground(Color.BLUE);

        //get the slider
        slider = findViewById(R.id.sv_slider);
        slider.setOnSeekBarChangeListener(this);

        failTv = findViewById(R.id.tvFail);
        successTv = findViewById(R.id.tvSuccess);

        //set the initial text color
        updateTextColors(50);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "Progress : " + progress);
        setViewBackground(ColorUtils.getColor(progress / 100f));
        updateTextColors(progress);
        if (slideListener != null)
            if (progress == 5) {

                //slide negative
                slideListener.onSlideFinished(false);
            } else if (progress == 95) {

                //slide positive
                slideListener.onSlideFinished(true);
            }

    }

    private void updateTextColors(int progress) {
        if (progress > 50) {
            successTv.setAlpha(1 - (progress - 50) / 100f);
            failTv.setAlpha(0f);
        } else if (progress < 50) {
            failTv.setAlpha(1 - (progress + 50) / 100f);
            successTv.setAlpha(0f);
        } else if (progress == 50) {
            failTv.setTextColor(Color.WHITE);
            successTv.setTextColor(Color.WHITE);
            successTv.setAlpha(1f);
            failTv.setAlpha(1f);
        }

    }

    private void setViewBackground(int color) {
        Drawable bg = ContextCompat.getDrawable(getContext(), R.drawable.sv_bg);
        ColorUtils.setDrawableColor(bg, color);
        setBackgroundDrawable(bg);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setSlideListener(OnSlideCallback slideListener) {
        this.slideListener = slideListener;
    }


    public interface OnSlideCallback {
        void onSlideFinished(boolean which);
    }
}
