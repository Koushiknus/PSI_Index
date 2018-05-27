package com.sp.psi.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sp.psi.gateway.APIGateway;
import com.sp.psi.interfaces.IAPIListener;
import com.sp.psi.utils.Const;

import java.util.TreeMap;

/**
 * Created by Koushik on 5/26/2018.
 */
public class PSIController {

    protected Context mContext;
    protected TreeMap<String, String> mValues;

    protected IAPIListener iapiListener;

    public PSIController(Context context, IAPIListener iapiListener) {

        rebindListener(context, iapiListener);
    }

    public void rebindListener(Context context, IAPIListener iapiListener) {
        this.mContext = context;
        this.iapiListener = iapiListener;
    }

    private class PSIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIGateway.FETCH_INFO:
                    switch (msg.arg1) {
                        case 0:
                            String title = msg.getData().getString(Const.ASYNCTASK_TITLE);
                            String message = msg.getData().getString(Const.ASYNCTASK_MESSAGE);
                            processFetchPSIInfoFailed(title, message);
                            break;
                        case 1:
                            processFetchPSIInfoSuccess();
                            break;
                    }
                    break;


            }
        }
    }
    private void processFetchPSIInfoFailed(String title,String message){

    }

    private void processFetchPSIInfoSuccess(){
        
    }
}

