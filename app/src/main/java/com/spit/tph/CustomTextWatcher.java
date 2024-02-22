package com.spit.tph;

import android.text.Editable;
import android.text.TextWatcher;

import com.spit.tph.Event.CustomTextWatcherEvent;

import org.greenrobot.eventbus.EventBus;

public class CustomTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        EventBus.getDefault().post(new CustomTextWatcherEvent(charSequence.toString()));
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
