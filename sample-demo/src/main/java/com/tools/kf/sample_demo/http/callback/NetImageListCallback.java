package com.tools.kf.sample_demo.http.callback;

import com.google.gson.Gson;
import com.tools.kf.sample_demo.http.model.ResponseImagesListEntity;
import com.tools.kf.sample_demo.http.model.ResponseNetImageEntity;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

public abstract class NetImageListCallback extends Callback<ResponseNetImageEntity>
{
    @Override
    public ResponseNetImageEntity parseNetworkResponse(Response response) throws IOException
    {
        String string = response.body().string();
        ResponseNetImageEntity responseImagesListEntity = new Gson().fromJson(string, ResponseNetImageEntity.class);
        return responseImagesListEntity;
    }
}