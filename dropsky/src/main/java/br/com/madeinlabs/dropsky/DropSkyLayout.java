package br.com.madeinlabs.dropsky;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;

public class DropSkyLayout extends RelativeLayout {
    private DropSkyAdapter mAdapter;
    private int mNextViewIndex;
    private float mGround;
    private long mDropDuration;
    private DropSkyListener mListener;

    public DropSkyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(DropSkyAdapter adapter) {
        this.mAdapter = adapter;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = mAdapter.getTotalHeight();
    }

    /**
     * This method show the child without animation
     */
    public void drop() {
        drop(0);
    }

    /**
     * This method show the child with animation
     * @param duration the duration of the animation
     */
    public void drop(long duration) {
        if(getChildCount() > 0) {
            removeAllViews();
        }

        mNextViewIndex = mAdapter.getCount() - 1;
        mDropDuration = duration;
        mGround = 0;

        dropItem();
    }

    private void dropItem() {
        if(mNextViewIndex >= 0) {
            final DropSkyItem dropSkyItem = mAdapter.getDropSkyItem(mNextViewIndex);
            addView(dropSkyItem);
            ViewGroup.LayoutParams layoutParams = dropSkyItem.getLayoutParams();
            layoutParams.height = mAdapter.getTotalHeight();


            //start item on the top of the view
            dropSkyItem.setTranslationY(-getHeight());
            //translate the item until the top of the other view, the current "ground"
            long currentDropDuration = mDropDuration/mAdapter.getCount();
            ViewPropertyAnimator animator = dropSkyItem.animate().translationY(mGround).setDuration(currentDropDuration);

            mNextViewIndex--;
            animator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    if(mListener != null) {
                        mListener.onItemDropStart(dropSkyItem.mViewContainer, mNextViewIndex + 1);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if(mListener != null) {
                        mListener.onItemDropEnd(dropSkyItem.mViewContainer, mNextViewIndex + 1);
                    }
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
        } else {
            if (mListener != null) {
                mListener.onDropEnd();
            }
        }
    }

    public void setListener(DropSkyListener listener) {
        this.mListener = listener;
    }

    public void fly(int flyDuration) {
        animate().setDuration(flyDuration).translationY(- mAdapter.getTotalHeight());
    }

    public interface DropSkyListener {
        void onItemDropEnd(View view, int index);
        void onItemDropStart(View view, int index);
        void onDropEnd();
    }
}
