package com.spit.tph.WebService.Callback;

import android.util.Log;

import com.spit.tph.Event.BorrowListAssets;
import com.spit.tph.Event.CallbackFailEvent;
import com.spit.tph.Event.CallbackResponseEvent;
import com.spit.tph.Event.CallbackStartEvent;
import com.spit.tph.MainActivity;
import com.spit.tph.R;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBorrowListAssetCallback implements Callback<BorrowListAssets> {
    private String id;

    public GetBorrowListAssetCallback() {
        EventBus.getDefault().post(new CallbackStartEvent());
    }

    public GetBorrowListAssetCallback(String id) {
        EventBus.getDefault().post(new CallbackStartEvent());
        this.id = id;
    }

    @Override
    public void onResponse(Call<BorrowListAssets> call, Response<BorrowListAssets> response) {
        Log.i("onResponse", "onResponse " + response.raw().message().toString() + " " + call.request().toString());
        if(response.code() == 200) {
            if(id == null) {
                EventBus.getDefault().post(new CallbackResponseEvent(response.body()));
            } else {
                EventBus.getDefault().post(new CallbackResponseEvent(id, null, response.body()));
            }
        } else {
            EventBus.getDefault().post(new CallbackFailEvent(MainActivity.mContext.getString(R.string.no_internet)) );
        }
    }

    @Override
    public void onFailure(Call<BorrowListAssets> call, Throwable t) {
        Log.i("onFailure", "onFailure" + t.toString() + " " + call.request().url());

        EventBus.getDefault().post(new CallbackFailEvent(MainActivity.mContext.getString(R.string.no_internet)) );

        //EventBus.getDefault().post(new CallbackFailEvent(t.getMessage()));
    }
}
