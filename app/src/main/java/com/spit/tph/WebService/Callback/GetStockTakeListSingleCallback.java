package com.spit.tph.WebService.Callback;

import com.spit.tph.Entity.StockTakeList;
import com.spit.tph.Event.CallbackFailEvent;
import com.spit.tph.Event.CallbackResponseEvent;
import com.spit.tph.Event.CallbackStartEvent;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetStockTakeListSingleCallback implements Callback<StockTakeList> {
        public GetStockTakeListSingleCallback() {
            EventBus.getDefault().post(new CallbackStartEvent());
        }

        @Override
        public void onResponse(Call<StockTakeList> call, Response<StockTakeList> response) {
            if(response.code() == 200)
                EventBus.getDefault().post(new CallbackResponseEvent(response.body()));
            else
                EventBus.getDefault().post(new CallbackFailEvent(response.message()));

        }

        @Override
        public void onFailure(Call<StockTakeList> call, Throwable t) {

        }

}

