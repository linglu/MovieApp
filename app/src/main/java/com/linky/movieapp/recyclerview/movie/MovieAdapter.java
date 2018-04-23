package com.linky.movieapp.recyclerview.movie;

import android.content.Context;

import com.linky.movieapp.R;
import com.linky.movieapp.bean.MovieBean;
import com.linky.movieapp.recyclerview.MultiItemRecycleViewAdapter;
import com.linky.movieapp.recyclerview.MultiItemTypeSupport;
import com.linky.movieapp.recyclerview.ViewHolderHelper;
import com.linky.movieapp.widget.enlargeImgView.PhotoView;

/**
 * Created by oeasy on 2018/1/10.
 */

public class MovieAdapter extends MultiItemRecycleViewAdapter<MovieBean> {

    private OnClickImgListener onClickImgListener;

    public void setOnClickImgListener(OnClickImgListener onClickImgListener) {
        this.onClickImgListener = onClickImgListener;
    }

    public MovieAdapter(Context context, MultiItemTypeSupport<MovieBean> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }

    @Override
    public void convert(ViewHolderHelper helper, MovieBean movieBean) {
        helper.setImageUrl(R.id.iv_header, movieBean.img_url, R.drawable.ic_image_loading);

//        helper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickImgListener.onClickImg(movieBean.img_url);
//            }
//        }, R.id.iv_header);

/*

        ViewUtils.onClicks(helper.getConvertView(), new ViewAction(R.id.iv_header, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                onClickImgListener.onClickImg(movieBean.img_url);
            }
        }
        ));
*/

        helper.getConvertView().findViewById(R.id.iv_header).setOnClickListener(v -> {
            PhotoView pv = (PhotoView) v;//点击前获取的info和点击后获取的info是不一样的
            onClickImgListener.onClickImg(movieBean.img_url, pv.getInfo());
        });

//        ViewUtils.onClicks(helper.getConvertView(), ViewAction.newInstance(R.id.iv_header,
//                aVoid -> {
//                    info =
//                    onClickImgListener.onClickImg(movieBean.img_url, info);
//                }));


        helper.setText(R.id.tv_title, movieBean.title);
        helper.setText(R.id.tv_director, movieBean.director);
        helper.setText(R.id.tv_pubtime, movieBean.publish_time);
    }

}
