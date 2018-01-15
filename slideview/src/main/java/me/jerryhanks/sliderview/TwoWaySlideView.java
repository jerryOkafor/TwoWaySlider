package me.jerryhanks.sliderview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
    private LayerDrawable thumb;
    private TextView tvPosition;

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

        thumb = (LayerDrawable) slider.getThumb();
//        thumb.mutate();

        failTv = findViewById(R.id.tvFail);
        successTv = findViewById(R.id.tvSuccess);
        tvPosition = findViewById(R.id.tvPosition);

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
            float alphaToRight = (1-progress) / 100f;
            successTv.setAlpha(alphaToRight);
            failTv.setAlpha(0f);

            tvPosition.setText(successTv.getText());
            tvPosition.setVisibility(VISIBLE);

            thumb.findDrawableByLayerId(R.id.thumbPointerLeft).setAlpha((int) alphaToRight);
            thumb.findDrawableByLayerId(R.id.thumbPointerRight).setAlpha(255);
        } else if (progress < 50) {
            float alphaToLeft = (1-progress) / 100f;
            failTv.setAlpha(alphaToLeft);
            successTv.setAlpha(0f);

            tvPosition.setText(failTv.getText());
            tvPosition.setVisibility(VISIBLE);

            thumb.findDrawableByLayerId(R.id.thumbPointerLeft).setAlpha(255);
            thumb.findDrawableByLayerId(R.id.thumbPointerRight).setAlpha((int) alphaToLeft);
        } else if (progress == 50) {
            thumb.findDrawableByLayerId(R.id.thumbPointerLeft).setAlpha(255);
            thumb.findDrawableByLayerId(R.id.thumbPointerRight).setAlpha(255);

            tvPosition.setVisibility(INVISIBLE);


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
