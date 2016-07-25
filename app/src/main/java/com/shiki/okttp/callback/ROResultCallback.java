package com.shiki.okttp.callback;

import com.google.gson.Gson;
import com.std.ramsoapp.domain.ROResult;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Maik on 2016/1/29.
 */
public abstract class ROResultCallback extends Callback<ROResult> {

    @Override
    public ROResult parseNetworkResponse(Response response) throws IOException {
        String str = response.body().string();
        ROResult res = new Gson().fromJson(str, ROResult.class);
        return res;
    }
}
