package br.com.madeinlabs.dropskytest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.LinkedList;
import java.util.List;

import br.com.madeinlabs.dropsky.DropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyItem;
import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    private DropSkyLayout mDropSkyLayout;
    private CoordinatorLayout mRoot;
    private Toolbar mToolbar;
    private DropSkyItem mDropSkyItemSelected;
    private RelativeLayout mLayoutInterpolatorChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRoot = (CoordinatorLayout) findViewById(R.id.layout_root);
        mDropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);
        mLayoutInterpolatorChooser = (RelativeLayout) findViewById(R.id.layout_interpolator_chooser);

        mDropSkyLayout.setListener(new DropSkyLayout.DropSkyListener() {
            @Override
            public void onItemShowEnd(View view, int index) {
                YoYo.with(Techniques.Wave).duration(200).interpolate(new DecelerateInterpolator()).playOn(view);
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAdapter(false);
        refreshDropSkyWithoutAnimation();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void setUpAdapter(boolean reverseMode) {
        CustomDropSkyAdapter adapter = (CustomDropSkyAdapter) mDropSkyLayout.getAdapter();
        if (adapter != null) {
            if (adapter.isReverse() == !reverseMode && !adapter.isReverse() == reverseMode) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) mToolbar.getBackground();
                if (!reverseMode) {
                    transitionDrawable.reverseTransition(500);
                } else {
                    transitionDrawable.startTransition(500);
                }
            }
        }

        String mode;
        if (reverseMode) {
            mode = getString(R.string.reverse);
        } else {
            mode = getString(R.string.normal);
        }
        setTitle(getString(R.string.drop_sky_mode, mode));

        adapter = new CustomDropSkyAdapter(this, reverseMode);
        adapter.addItem(R.drawable.pikachu, "Show", R.color.one);
        adapter.addItem(R.drawable.insignia, "Hide", R.color.two);
        adapter.addItem(R.drawable.pokeball, "Refresh", R.color.three);
        adapter.addItem(R.drawable.egg_incubator, "Turn On Normal Mode", R.color.four);
        adapter.addItem(R.drawable.razz_berry, "Turn On Reverse Mode", R.color.five);
        adapter.addItem(R.drawable.psyduck, "Choose Interpolation", R.color.six);
        adapter.addItem(R.drawable.pokedex, "Pokedex", R.color.seven);
        adapter.addItem(R.drawable.gotcha, "Gotcha", R.color.eight);
        adapter.setOnItemClickListener(new DropSkyAdapter.Listener() {
            @Override
            public void onItemClicked(DropSkyItem dropSkyItem, int index) {
                mDropSkyItemSelected = dropSkyItem;
                switch (index) {
                    case 0:
                        show();
                        break;
                    case 1:
                        hide(dropSkyItem);
                        break;
                    case 2:
                        refreshDropSkyWithoutAnimation();
                        break;
                    case 3:
                        setUpAdapter(false);
                        break;
                    case 4:
                        setUpAdapter(true);
                        break;
                    case 5:
                        showHideInterpolatorChooser(dropSkyItem, true);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Option not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDropSkyLayout.setAdapter(adapter);
    }

    private void showHideInterpolatorChooser(DropSkyItem dropSkyItem, final boolean show) {
        mLayoutInterpolatorChooser.setBackgroundColor(dropSkyItem.getColor());
        Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (show) {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setHomeButtonEnabled(true);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                } else {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setHomeButtonEnabled(false);
                        actionBar.setDisplayHomeAsUpEnabled(false);
                    }
                    mLayoutInterpolatorChooser.setVisibility(View.INVISIBLE);
                }
            }
        };

        View showView;
        View hideView;
        if(show) {
            showView = mLayoutInterpolatorChooser;
            hideView = dropSkyItem.mViewItem;
        } else {
            showView = dropSkyItem.mViewItem;
            hideView = mLayoutInterpolatorChooser;
        }

        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new LinkedList<>();
        Animator animatorOut;
        Animator animatorIn;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int[] outLocation = new int[2];
            dropSkyItem.mViewItem.getLocationOnScreen(outLocation);
            int x = dropSkyItem.mViewItem.getWidth()/2;
            int y = outLocation[1];

            int finalRadius = Math.max(mDropSkyLayout.getWidth(), mDropSkyLayout.getHeight()) / 2;
            if(show) {
                animatorIn = ViewAnimationUtils.createCircularReveal(mLayoutInterpolatorChooser, x, y, 0, finalRadius);
                animatorOut = ObjectAnimator.ofFloat(dropSkyItem.mViewItem, View.ALPHA, 1, 0);
            } else {
                animatorIn = ObjectAnimator.ofFloat(dropSkyItem.mViewItem, View.ALPHA, 0, 1);
                animatorOut = ViewAnimationUtils.createCircularReveal(mLayoutInterpolatorChooser, x, y, finalRadius, 0);
            }
        } else {
            showView.setAlpha(0f);
            animatorIn = ObjectAnimator.ofFloat(showView, View.ALPHA, 0, 1);
            animatorOut = ObjectAnimator.ofFloat(hideView, View.ALPHA, 1, 0);
        }

        animatorIn.setDuration(500);
        animatorOut.setDuration(500);
        animatorOut.addListener(listener);

        animators.add(animatorOut);
        animators.add(animatorIn);
        animatorSet.playTogether(animators);
        mLayoutInterpolatorChooser.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    private void refreshDropSkyWithoutAnimation() {
        mDropSkyLayout.show();
    }

    private boolean show() {
        return mDropSkyLayout.show(2500);
    }

    private void hide(DropSkyItem dropSkyItem) {
        mRoot.setBackgroundColor(dropSkyItem.getColor());
        mDropSkyLayout.setBackgroundColor(dropSkyItem.getColor());

        boolean hiding = mDropSkyLayout.hide(1000);
        if(hiding) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (mLayoutInterpolatorChooser.getVisibility() == View.VISIBLE) {
                    showHideInterpolatorChooser(mDropSkyItemSelected, false);
                } else {
                    boolean showing = show();
                    if (showing) {
                        ActionBar actionBar = getSupportActionBar();
                        if(actionBar != null) {
                            actionBar.setHomeButtonEnabled(false);
                            actionBar.setDisplayHomeAsUpEnabled(false);
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
