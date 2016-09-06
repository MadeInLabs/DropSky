package br.com.madeinlabs.dropsky;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

public class DropSkyLayout extends RelativeLayout {
    private DropSkyAdapter mAdapter;
    private int mNextViewIndex;
    private float mGround;
    private long mDurationEachView;

    public DropSkyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(DropSkyAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void drop(long duration) {
        mNextViewIndex = mAdapter.getCount() - 1;
        mDurationEachView = duration / mAdapter.getCount();
        mGround = 0;
        dropItem();
    }

    private void dropItem() {
        if(mNextViewIndex >= 0) {
            final DropSkyItem dropSkyItem = mAdapter.getItem(mNextViewIndex);
            addView(dropSkyItem);

            //start item on the top of the view
            dropSkyItem.setTranslationY(-getHeight());
            //translate the item until the top of the other view, the current "ground"
            ViewPropertyAnimator animator = dropSkyItem.animate().translationY(mGround).setDuration(mDurationEachView);

            mNextViewIndex--;
            animator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //update the Ground
                    mGround -= dropSkyItem.getTrueHeight();
                    dropItem();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
    }
}
