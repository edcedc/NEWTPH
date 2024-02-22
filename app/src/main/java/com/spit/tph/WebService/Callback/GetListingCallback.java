package com.spit.tph.WebService.Callback;

import android.util.Log;

import com.spit.tph.Event.CallbackFailEvent;
import com.spit.tph.Event.CallbackResponseEvent;
import com.spit.tph.Event.CallbackStartEvent;
import com.spit.tph.Event.UpdateFailEvent;
import com.spit.tph.MainActivity;
import com.spit.tph.R;
import com.spit.tph.Response.ListingResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListingCallback implements Callback<ListingResponse> {
    public GetListingCallback() {
        EventBus.getDefault().post(new CallbackStartEvent());
    }

    @Override
    public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
        Log.i("onResponse", "onResponse" + response.toString());

        if(response.code() == 200)
            EventBus.getDefault().post(new CallbackResponseEvent(response.body()));
        else {
            EventBus.getDefault().post(new CallbackFailEvent(MainActivity.mContext.getString(R.string.no_internet)));

            EventBus.getDefault().post(new UpdateFailEvent());

        }
    }

    @Override
    public void onFailure(Call<ListingResponse> call, Throwable t) {
        EventBus.getDefault().post(new UpdateFailEvent());

        EventBus.getDefault().post(new CallbackFailEvent(t.getMessage()));
    }
}
