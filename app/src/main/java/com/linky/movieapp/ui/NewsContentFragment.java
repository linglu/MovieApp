package com.linky.movieapp.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.WebDefaultSettingsManager;
import com.linky.movieapp.R;
import com.linky.movieapp.utils.L;
import com.linky.movieapp.widget.WebLayout;


public class NewsContentFragment extends Fragment {


    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated = false;

    private String mUrl;
    private AgentWeb mAgentWeb;
    private AgentWeb.PreAgentWeb mPreAgentWeb;

    private Activity _Activity;

    public static NewsContentFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("news_url", url);
        NewsContentFragment fragment = new NewsContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _Activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUrl = getArguments().getString("news_url");
        L.i("onViewCreated " + mUrl);
        mPreAgentWeb = AgentWeb.with(this)
                //传入AgentWeb的父控件
                .setAgentWebParent((ViewGroup) view, new LinearLayout.LayoutParams(-1, -1))
                //设置进度条颜色与高度-1为默认值，2单位为dp
                .setIndicatorColorWithHeight(-1, 2)
                //设置 AgentWebSettings
                .setAgentWebWebSettings(getSettings())
                .setSecurityType(AgentWeb.SecurityType.strict)
                .setWebLayout(new WebLayout(getActivity()))
                .createAgentWeb()       // 创建 AgentWeb
                .ready();                // 设置 WebSettings
        mAgentWeb = mPreAgentWeb.go(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean f) {
        return fetchData(f);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if (isVisibleToUser) {
            if (_Activity != null) {
                ((MoviesDetailActivity) _Activity).setTitleCallback();
            }
        }

        prepareFetchData(false);
    }

    public boolean fetchData(boolean forceUpdate) {

        boolean loadData = isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate);

        L.i("fetchData load " + mUrl + ", isDataInitiated " + isDataInitiated + ", loadData : " + loadData);

        if (loadData) {
            mPreAgentWeb.go(mUrl);          // WebView载入该url地址的页面并显示。
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public AgentWebSettings getSettings() {
        return new WebDefaultSettingsManager() {

            @Override
            public AgentWebSettings toSetting(WebView webView) {
                super.toSetting(webView);
                getWebSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                getWebSettings().setUseWideViewPort(true);
                getWebSettings().setLoadWithOverviewMode(true);
                return this;
            }
        };
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        L.i("NewsContentFragment : onDestroyView " + "");
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
        isDataInitiated = false;
    }

}
