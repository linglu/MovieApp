package com.linky.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.linky.movieapp.R;
import com.linky.movieapp.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetailActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    private List<String> mNewsUrls = new ArrayList<>();
    private List<String> mNewsTitle = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        ButterKnife.bind(this);

        Intent in = getIntent();
        if (in != null) {

            mNewsUrls.addAll(in.getStringArrayListExtra("movies_url_list"));
            mNewsTitle.addAll(in.getStringArrayListExtra("movies_title"));
            int position = in.getIntExtra("movies_position", 0);

            MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(adapter);
            mViewPager.setCurrentItem(position, false);

            ViewUtils.onClick(mIvBack, aVoid -> finish());
            mTvTitle.setText(mNewsTitle.get(position));
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsContentFragment.newInstance(mNewsUrls.get(position));
        }

        @Override
        public int getCount() {
            return mNewsUrls.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    public void setTitleCallback() {
        int position = mViewPager.getCurrentItem();
        mTvTitle.setText(mNewsTitle.get(position));
    }

}
