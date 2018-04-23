package com.linky.movieapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.linky.movieapp.App;
import com.linky.movieapp.R;
import com.linky.movieapp.bean.MovieBean;
import com.linky.movieapp.mvp.presenter.MainPrensenter;
import com.linky.movieapp.mvp.view.MainView;
import com.linky.movieapp.recyclerview.OnItemClickListener;
import com.linky.movieapp.recyclerview.loadmore.LoadMoreRecyclerViewAdapter;
import com.linky.movieapp.recyclerview.movie.GetImageCacheTask;
import com.linky.movieapp.recyclerview.movie.MovieAdapter;
import com.linky.movieapp.recyclerview.movie.MovieMulti;
import com.linky.movieapp.recyclerview.movie.OnClickImgListener;
import com.linky.movieapp.utils.AsyncRun;
import com.linky.movieapp.utils.L;
import com.linky.movieapp.widget.enlargeImgView.Info;
import com.linky.movieapp.widget.enlargeImgView.PhotoView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;

public class MainActivity extends BaseActivity implements MainView, OnItemClickListener, OnClickImgListener, GetImageCacheTask.OnGetImgCacheUrl {

    @BindView(R.id.recycler_movies)
    RecyclerView mRVMovies;

    @BindView(R.id.bg)
    ImageView bg;

    @BindView(R.id.img)
    PhotoView mImg;

    @BindView(R.id.parent)
    FrameLayout parent;


    @Inject
    MainPrensenter mMainPrensenter; //每一个需要用到的对象都需要被实例化，只有被实例化才能用它的方法



    private MovieAdapter mMovieAdapter;


    private int page = 0;

    Info mInfo;

    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPrensenter.getMovie(1);

        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRVMovies.setLayoutManager(linearLayoutManager);
        mMovieAdapter = new MovieAdapter(this, new MovieMulti());
        mMovieAdapter.setOnClickImgListener(this);
        LoadMoreRecyclerViewAdapter mainAdapter = new LoadMoreRecyclerViewAdapter(mMovieAdapter);
        mRVMovies.setAdapter(mainAdapter);
        mRVMovies.addOnScrollListener(getOnScrollListener());
        ((MovieAdapter) mainAdapter.getInnerAdapter()).setOnItemClickListener(this);


        mImg.setScaleType(ImageView.ScaleType.FIT_XY);
        mImg.enable();
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bg.startAnimation(out);
                if(mInfo != null){
                    mImg.animaTo(mInfo, new Runnable() {
                        @Override
                        public void run() {
                            parent.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void initInject() {
        App.getInjectComponent().inject(this);
    }

    @Override
    protected void attachViewToPresenter() {
        mMainPrensenter.attachView(this, this);
    }

    @Override
    protected void detachViewToPresenter() {
        L.e("mMainPrensenter", mMainPrensenter.toString());
        mMainPrensenter.detachView();
    }

    @Override
    public void onGetMovie(List<MovieBean> movieBeanList, int code, int totalPage) {
        L.e("movieBeanList", movieBeanList.size() + "");
        if (movieBeanList != null) {
            mMovieAdapter.addAll(movieBeanList);
            if (mRVMovies != null) {
                page++;
                updateRecyclerState(mRVMovies, movieBeanList.size(), page, 10);
            }
        }

    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object o, int position) {
        // 进入电影详情页面
        final ArrayList<String> urls = new ArrayList<>();
        final ArrayList<String> newsTitles = new ArrayList<>();
        Observable.from(mMovieAdapter.getAll()).forEach(movieBean -> {
            urls.add(movieBean.url);
            newsTitles.add(movieBean.title);
        });

        Intent intent = new Intent(this, MoviesDetailActivity.class);
        intent.putExtra("movies_url_list", urls);
        intent.putExtra("movies_title", newsTitles);
        intent.putExtra("movies_position", position);
        startActivity(intent);
        
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }


    @Override
    public void loadNextPage(int pageNumber, int pageSize) {
        Log.e("loadNextPage", "loadNextPage");
        AsyncRun.runDelayOnUiThread(() -> {
            mMainPrensenter.getMovie(pageNumber);
        }, 3000);
    }

    @Override
    public void onClickImg(String url,Info info) {
        Log.e("onClickImg", "点我实现放大图片功能撒！！！");
        Log.e("imgUrl", url);

        GetImageCacheTask getImageCacheTask = new GetImageCacheTask(this);
        getImageCacheTask.setOnGetImgCacheUrl(this);
        getImageCacheTask.execute(url);

        mInfo = info;

    }

    @Override
    public void getImgCacheUrl(String string) {
        Log.e("getImgCacheUrl", string);//Glide缓存地址

        Bitmap bitmap = BitmapFactory.decodeFile(string);

        mImg.setImageBitmap(bitmap);
        bg.startAnimation(in);
        bg.setVisibility(View.VISIBLE);
        parent.setVisibility(View.VISIBLE);
        mImg.animaFrom(mInfo);


    }

    @Override
    public void onBackPressed() {
        if (parent.getVisibility() == View.VISIBLE) {
            bg.startAnimation(out);
            mImg.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    parent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }



}
