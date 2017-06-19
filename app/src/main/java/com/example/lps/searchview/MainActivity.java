package com.example.lps.searchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * 搜索
     */
    private TextView mTvSearch;
    private LinearLayout mLlSearchview;
    private Toolbar mToolbar;
    private ScrollView mScrollView;
    private ImageView mIvs;
    boolean isexpand = false;
    TransitionSet mSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mLlSearchview = (LinearLayout) findViewById(R.id.ll_searchview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mToolbar.getBackground().mutate().setAlpha(0);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                changeAlpha();
                if (mScrollView.getScrollY() >= mIvs.getHeight() - mToolbar.getHeight() && !isexpand) {
                    expand();
                } else if (mScrollView.getScrollY() <= 0 && isexpand) {
                    reduce();
                }
            }
        });
        mIvs = (ImageView) findViewById(R.id.ivs);
    }

    private void reduce() {
        isexpand = false;
        mTvSearch.setText("搜索");
        RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) mLlSearchview.getLayoutParams();
        mLayoutParams.width = dp2px(100);
        mLayoutParams.setMargins(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
        mLlSearchview.setLayoutParams(mLayoutParams);
        beginDelayTransition(mLlSearchview);
    }

    private void beginDelayTransition(LinearLayout mLlSearchview) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(mLlSearchview, mSet);
    }

    private void expand() {
        isexpand = true;
        mTvSearch.setText("搜索朋友和简书上的内容");
        RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) mLlSearchview.getLayoutParams();
        mLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        mLayoutParams.setMargins(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
        mLlSearchview.setLayoutParams(mLayoutParams);
        beginDelayTransition(mLlSearchview);
    }

    private void changeAlpha() {
        int mScrollY = mScrollView.getScrollY();
        if (mScrollY < 0) {
            mToolbar.getBackground().mutate().setAlpha(0);
            return;
        }
        float radio = Math.min(1, mScrollY / (mIvs.getHeight() - mToolbar.getHeight() * 1.0f));
        mToolbar.getBackground().mutate().setAlpha((int) (radio * 0xff));
    }

    private int dp2px(int dpvalue) {
        float mDensity = getResources().getDisplayMetrics().density;
        return (int) (dpvalue * mDensity + 0.5f);
    }
}
