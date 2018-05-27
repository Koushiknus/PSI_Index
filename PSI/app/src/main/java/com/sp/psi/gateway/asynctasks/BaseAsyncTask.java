package com.sp.psi.gateway.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sp.psi.utils.Const;

import java.util.TreeMap;

/**
 * Created by Koushik on 5/27/2018.
 */
public class BaseAsyncTask extends AsyncTask<Object, Integer, Message> {

    protected Handler mHandler;
    protected TreeMap<String, String> mInputData;
    protected JSONRequestsender sender;
    protected JSONResponseReader respReader;
    protected Context mContext;
    protected Message msg = new Message();
    protected int mGatewayType;

    @Override
    protected Message doInBackground(Object... params) {
        return null;
    }

    protected String getString(int stringId) {
        return mContext.getString(stringId);
    }

    protected Message createMessage() {
        msg.arg1 = 1;
        return msg;
    }

    protected Message createMessage(String title, String message) {
        msg.arg1 = 0;
        Bundle bundle = new Bundle();
        bundle.putString(Const.ASYNCTASK_TITLE, title);
        bundle.putString(Const.ASYNCTASK_MESSAGE, message);
        msg.setData(bundle);
        return msg;
    }

    protected Message createMessage(int status) {
        msg.arg1 = status;
        return msg;
    }
}
