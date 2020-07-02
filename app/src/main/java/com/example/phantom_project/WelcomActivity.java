package com.example.phantom_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class WelcomActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button mSkipBnt, mNextBtn;
    private PreferenceManage preferenceManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checking for fistTime launch -before calling set contenview
        preferenceManage = new PreferenceManage(this);
        if (!preferenceManage.isFistTimelauch()) {
            launchHomeScreen();
            finish();
        }
        //making notifiaction bar tranpatrent
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }
        setContentView(R.layout.activity_welcom);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layout_linear);
        mSkipBnt = findViewById(R.id.btn_skip);
        mNextBtn = findViewById(R.id.btn_next);

        //layoutof all wellcom sliced
        layouts = new int[]{
                R.layout.nav_head_home,
                R.layout.welcom1,
                R.layout.nav_head_home

        };
        addBottomDots(0);
        changeStatusBarColor();
        viewPageAdapter = new ViewPageAdapter();
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        mSkipBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking last page
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        preferenceManage.setFistTimelaunch(false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorInActive = getResources().getIntArray(R.array.array_dot_inactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive[currentPage]);
            dotsLayout.addView(dots[i]);

        }
        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorActive[currentPage]);
        }
    }

    //view pager listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            addBottomDots(position);
            //changethe next button text  next>got it
            if (position == layouts.length - 1) {
                mNextBtn.setText("Đã hiểu");
                mSkipBnt.setVisibility(View.GONE);
            } else {
                mNextBtn.setText("Tiếp");
                mSkipBnt.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    //notificationbar tranparent
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //View pager Adapter
    public class ViewPageAdapter extends PagerAdapter {
        private LayoutInflater inflater;


        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
