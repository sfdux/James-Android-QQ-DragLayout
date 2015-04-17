package com.example.james.jamesdraglayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by James.Shi on 11/26/14.
 */
public class TouchResultReceiver extends ResultReceiver {
    private IReceiver mReceiver;

    public TouchResultReceiver(Handler handler) {
        super(handler);
    }

    public interface IReceiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(IReceiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

}
