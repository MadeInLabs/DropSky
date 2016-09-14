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
    private long mDropDuration;
    private DropSkyListener mListener;
    private boolean isShowing;

    public DropSkyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListener = new EmptyDropSkyListener();
        isShowing = false;
    }

    /**
     * Set the adapter to this DropSkyLayout.
     * @param adapter adds the items to DropSkyLayout and decides if the animation goes: top-down or
     *                bottom-up.
     */
    public void setAdapter(DropSkyAdapter adapter) {
        this.mAdapter = adapter;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = mAdapter.getTotalHeight();
    }

    /**
     The DropSkyLayout hide yourself with animation
     @param hideDuration is the duration of animation
     */
    public void hide(int hideDuration) {
        int translateYValue = -mAdapter.getTotalHeight();
        boolean reverse = mAdapter.isReverse();
        if (reverse) {
            translateYValue = -translateYValue;
        }

        if(hideDuration > 0) {
            animate().setDuration(hideDuration).translationY(translateYValue);
        } else {
            setTranslationY(translateYValue);
        }
    }

    /**
     The DropSkyLayout hide yourself without animation
     */
    public void hide() {
        hide(0);
    }

    /**
     * The DropSkyLayout show its items without animation
     */
    public void show() {
        show(0);
    }

    /**
     * The DropSkyLayout show its items with animation
     * @param duration the duration of animation
     */
    public void show(long duration) {
        if(!isShowing) {
            isShowing = true;
            setTranslationY(0);
            if (getChildCount() > 0) {
                removeAllViews();
            }

            boolean reverse = mAdapter.isReverse();
            if (reverse) {
                mNextViewIndex = 0;
            } else {
                mNextViewIndex = mAdapter.getCount() - 1;
            }
            mDropDuration = duration;

            showItem();
        }
    }

    /**
     *Shows next item on the adapter's order
     */
    private void showItem() {
        if(mNextViewIndex >= 0 && mNextViewIndex < mAdapter.getCount()) {
            int itemY = mAdapter.getItemY(mNextViewIndex);
            final DropSkyItem dropSkyItem = mAdapter.getDropSkyItem(mNextViewIndex);

            addView(dropSkyItem);
            ViewGroup.LayoutParams layoutParams = dropSkyItem.getLayoutParams();
            layoutParams.height = mAdapter.getTotalHeight();

            int initialY;
            if (mAdapter.isReverse()) {
                if (mDropDuration > 0) {
                    initialY = getHeight();
                } else {
                    initialY = itemY;
                }
            } else {
                if (mDropDuration > 0) {
                    initialY = -getHeight();
                } else {
                    int trueHeight = mAdapter.getTrueHeight(mNextViewIndex);
                    initialY = - mAdapter.getTotalHeight() + itemY + trueHeight;
                }
            }

            //start item on the top of the view
            dropSkyItem.setTranslationY(initialY);
            if(mDropDuration == 0) {
                onStartShowingItem(dropSkyItem);
                onEndShowingItem(dropSkyItem);
            } else {
                int translationYByValue;
                if (mAdapter.isReverse()) {
                    translationYByValue = -mAdapter.getTotalHeight() + itemY;
                } else {
                    translationYByValue = mAdapter.getTotalHeight() - mAdapter.getItemY(mAdapter.getCount() - mNextViewIndex - 1);
                }
                //translate the item until the top of the other view, the current "ground"
                long currentDropDuration = mDropDuration/mAdapter.getCount();
                ViewPropertyAnimator animator = dropSkyItem.animate().translationYBy(translationYByValue).setDuration(currentDropDuration);
                animator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        onStartShowingItem(dropSkyItem);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onEndShowingItem(dropSkyItem);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            }
        } else {
            mListener.onDropEnd();
            isShowing = false;
        }
    }

    private void onStartShowingItem(DropSkyItem dropSkyItem) {
        mListener.onItemDropStart(dropSkyItem.mViewContainer, mNextViewIndex + 1);
    }

    private void onEndShowingItem(DropSkyItem dropSkyItem) {
        if(mAdapter.isReverse()) {
            mNextViewIndex++;
        } else {
            mNextViewIndex--;
        }
        mListener.onItemDropEnd(dropSkyItem.mViewContainer, mNextViewIndex + 1);
        showItem();
    }

    public void setListener(DropSkyListener listener) {
        if(listener != null) {
            mListener = listener;
        } else {
            mListener = new EmptyDropSkyListener();
        }
    }

    public interface DropSkyListener {
        /**
         * It's called when each item finishes of been animated
         * @param view is the item that was animated
         * @param index is the index of the item that was animated
         */
        void onItemDropEnd(View view, int index);
        /**
         * It's called when each item starts of been animated
         * @param view is the item animated
         * @param index is the index of the item animated
         */
        void onItemDropStart(View view, int index);
        /**
         * It's called when all the items were animated
         */
        void onDropEnd();
    }

    private class EmptyDropSkyListener implements DropSkyListener {
        @Override
        public void onItemDropEnd(View view, int index) {
        }
        @Override
        public void onItemDropStart(View view, int index) {
        }
        @Override
        public void onDropEnd() {
        }
    }
}
