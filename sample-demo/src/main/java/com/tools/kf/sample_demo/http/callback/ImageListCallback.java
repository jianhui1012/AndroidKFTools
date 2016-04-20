package com.tools.kf.sample_demo.http.callback;

import com.google.gson.Gson;
import com.tools.kf.sample_demo.http.model.ResponseImagesListEntity;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import okhttp3.Response;

public abstract class ImageListCallback extends Callback<ResponseImagesListEntity>
{
    @Override
    public ResponseImagesListEntity parseNetworkResponse(Response response) throws IOException
    {
        String string = response.body().string();
        ResponseImagesListEntity responseImagesListEntity = new Gson().fromJson(string, ResponseImagesListEntity.class);
        return responseImagesListEntity;
    }
}