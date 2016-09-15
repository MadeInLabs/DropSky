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
    private boolean isChanging;

    public DropSkyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListener = new EmptyDropSkyListener();
        isChanging = false;
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
     * @param hideDuration is the duration of animation
     */
    public boolean hide(int hideDuration) {
        if(!isChanging) {
            isChanging = true;
            int translateYValue = -mAdapter.getTotalHeight();
            boolean reverse = mAdapter.isReverse();
            if (reverse) {
                translateYValue = -translateYValue;
            }

            if (hideDuration > 0) {
                animate().setDuration(hideDuration).translationY(translateYValue).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isChanging = false;
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            } else {
                setTranslationY(translateYValue);
                isChanging = false;
            }
            return true;
        }
        return false;
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
    public boolean show(long duration) {
        if(!isChanging) {
            isChanging = true;
            if (getChildCount() > 0) {
                removeAllViews();
            }
            setTranslationY(0);

            boolean reverse = mAdapter.isReverse();
            if (reverse) {
                mNextViewIndex = 0;
            } else {
                mNextViewIndex = mAdapter.getCount() - 1;
            }
            mDropDuration = duration;

            showItem();
            return true;
        }
        return false;
    }

    /**
     *Shows next item on the adapter's order
     */
    private void showItem() {
        int countTotalItems = mAdapter.getCount();
        if(mNextViewIndex >= 0 && mNextViewIndex < countTotalItems) {
            final DropSkyItem dropSkyItem = mAdapter.getDropSkyItem(mNextViewIndex);
            addView(dropSkyItem);
            ViewGroup.LayoutParams layoutParams = dropSkyItem.getLayoutParams();
            layoutParams.height = mAdapter.getTotalHeight();

            int finalY;
            if (mAdapter.isReverse()) {
                finalY = mAdapter.getItemY(mNextViewIndex);
            } else {
                int countItemsAlreadyDropped = countTotalItems - (mNextViewIndex + 1);
                int sizeAlreadyOccupied = mAdapter.getItemY(countItemsAlreadyDropped);
                finalY = -sizeAlreadyOccupied;
            }

            if(mDropDuration == 0) {
                onStartShowingItem(dropSkyItem);
                dropSkyItem.setTranslationY(finalY);
                onEndShowingItem(dropSkyItem);
            } else {
                int initialY;
                if (mAdapter.isReverse()) {
                    initialY = getHeight();
                } else {
                    initialY = -getHeight();
                }
                //start item on the top of the view
                dropSkyItem.setTranslationY(initialY);
                //translate the item until the top of the other view, the current "ground"
                long currentDropDuration = mDropDuration/ countTotalItems; //duration of the translation animation of this item
                ViewPropertyAnimator animator = dropSkyItem.animate().translationY(finalY).setDuration(currentDropDuration);
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
            isChanging = false;
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
