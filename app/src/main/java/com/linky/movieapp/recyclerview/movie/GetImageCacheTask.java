package com.linky.movieapp.recyclerview.movie;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * Created by oeasy on 2018/1/15.
 */

public class GetImageCacheTask extends AsyncTask<String,Void,File>{

    private Context context;

    private OnGetImgCacheUrl onGetImgCacheUrl;


    public GetImageCacheTask(Context context) {
        this.context = context;
    }

    public void setOnGetImgCacheUrl(OnGetImgCacheUrl onGetImgCacheUrl) {
        this.onGetImgCacheUrl = onGetImgCacheUrl;
    }

    @Override
    protected File doInBackground(String... params) {
        String imgUrl = params[0];
        try {
            return Glide.with(context)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(File file) {
        if (file == null){
            return;
        }
        String path = file.getPath();
        onGetImgCacheUrl.getImgCacheUrl(path);
    }


    public interface OnGetImgCacheUrl{
        void getImgCacheUrl(String string);
    }
}
