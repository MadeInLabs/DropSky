package br.com.madeinlabs.dropsky;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.List;

public class DropSkyLayout extends RelativeLayout {
    private DropSkyAdapter mAdapter;
    private DropSkyListener mListener;
    private boolean isChanging;
    private TimeInterpolator mInterpolatorByItem;

    public DropSkyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListener = new EmptyDropSkyListener();
        isChanging = false;
        mInterpolatorByItem = new LinearInterpolator();
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
                        mListener.onHideEnd();
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

            int countTotalItems = mAdapter.getCount();
            int index;
            if (mAdapter.isReverse()) {
                index = 0;
            } else {
                index = countTotalItems - 1;
            }

            List<Animator> animators = new LinkedList<>();
            while (index >= 0 && index < countTotalItems) {
                final DropSkyItem dropSkyItem = mAdapter.getDropSkyItem(index);
                addView(dropSkyItem);
                ViewGroup.LayoutParams layoutParams = dropSkyItem.getLayoutParams();
                layoutParams.height = mAdapter.getTotalHeight();

                int finalY;
                if (mAdapter.isReverse()) {
                    finalY = mAdapter.getItemY(index);
                } else {
                    int countItemsAlreadyDropped = countTotalItems - (index + 1);
                    int sizeAlreadyOccupied = mAdapter.getItemY(countItemsAlreadyDropped);
                    finalY = -sizeAlreadyOccupied;
                }

                if(duration == 0) {
                    onStartShowingItem(dropSkyItem, index);
                    dropSkyItem.setTranslationY(finalY);
                    onEndShowingItem(dropSkyItem, index);
                } else {
                    int initialY;
                    if (mAdapter.isReverse()) {
                        initialY = getHeight();
                    } else {
                        initialY = -getHeight();
                    }
                    long itemShowDuration = duration/ countTotalItems;
                    dropSkyItem.setTranslationY(initialY);

                    Animator animator = ObjectAnimator.ofFloat(dropSkyItem, View.TRANSLATION_Y, initialY, finalY);
                    animator.setDuration(itemShowDuration);
                    animator.setInterpolator(mInterpolatorByItem);
                    animators.add(animator);

                    final int finalI = index;
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            onStartShowingItem(dropSkyItem, finalI);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            onEndShowingItem(dropSkyItem, finalI);
                        }
                    });
                }

                if (mAdapter.isReverse()) {
                    index++;
                } else {
                    index--;
                }
            }

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(animators);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mListener.onShowEnd();
                    isChanging = false;
                }
            });
            animatorSet.start();
            return true;
        }
        return false;
    }

    private void onStartShowingItem(DropSkyItem dropSkyItem, int i) {
        mListener.onItemShowStart(dropSkyItem.mViewContainer, i);
    }

    private void onEndShowingItem(DropSkyItem dropSkyItem, int index) {
        mListener.onItemShowEnd(dropSkyItem.mViewContainer, index);
    }

    public void setListener(DropSkyListener listener) {
        if(listener != null) {
            mListener = listener;
        } else {
            mListener = new EmptyDropSkyListener();
        }
    }

    public DropSkyAdapter getAdapter() {
        return mAdapter;
    }

    public void setItemInterpolator(TimeInterpolator itemInterpolator) {
        mInterpolatorByItem = itemInterpolator;
    }

    public interface DropSkyListener {
        /**
         * It's called when each item finishes of been animated
         * @param view is the item that was animated
         * @param index is the index of the item that was animated
         */
        void onItemShowEnd(View view, int index);
        /**
         * It's called when each item starts of been animated
         * @param view is the item animated
         * @param index is the index of the item animated
         */
        void onItemShowStart(View view, int index);
        /**
         * It's called when all the items were animated
         */
        void onShowEnd();

        /**
         * It's called when the DropSky is hided
         */
        void onHideEnd();
    }

    private class EmptyDropSkyListener implements DropSkyListener {
        @Override
        public void onItemShowEnd(View view, int index) {
        }
        @Override
        public void onItemShowStart(View view, int index) {
        }
        @Override
        public void onShowEnd() {
        }
        @Override
        public void onHideEnd() {

        }
    }
}
