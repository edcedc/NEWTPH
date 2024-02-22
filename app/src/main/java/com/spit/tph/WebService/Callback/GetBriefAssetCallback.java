package com.spit.tph.WebService.Callback;

import android.util.Log;

import com.spit.tph.Entity.SPEntityP2.BriefAsset;
import com.spit.tph.Event.CallbackFailEvent;
import com.spit.tph.Event.CallbackResponseEvent;
import com.spit.tph.Event.CallbackStartEvent;
import com.spit.tph.Event.UpdateFailEvent;
import com.spit.tph.MainActivity;
import com.spit.tph.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBriefAssetCallback  implements Callback<List<BriefAsset>> {
    private int type;

    public GetBriefAssetCallback() {
        EventBus.getDefault().post(new CallbackStartEvent());
    }

    public GetBriefAssetCallback(int type) {
        this.type = type;
        EventBus.getDefault().post(new CallbackStartEvent());
        Log.i("CONTINUOUS", "CONTINUOUS case 10 a start");
    }

    @Override
    public void onResponse(Call<List<BriefAsset>> call, Response<List<BriefAsset>> response) {
        Log.i("onResponse", "onResponse" + response.toString());

        if(response.code() == 200) {
            CallbackResponseEvent callbackResponseEvent = new CallbackResponseEvent(response.body());
            callbackResponseEvent.type = type;
            EventBus.getDefault().post(callbackResponseEvent);
        } else {
            EventBus.getDefault().post(new UpdateFailEvent());
            EventBus.getDefault().post(new CallbackFailEvent(MainActivity.mContext.getString(R.string.no_internet)));
        }
    }

    @Override
    public void onFailure(Call<List<BriefAsset>> call, Throwable t) {
        Log.i("onFailure", "onFailure" + t.toString() + " " + call.request().url());
        EventBus.getDefault().post(new UpdateFailEvent());
        EventBus.getDefault().post(new CallbackFailEvent(MainActivity.mContext.getString(R.string.no_internet)));
    }
}
