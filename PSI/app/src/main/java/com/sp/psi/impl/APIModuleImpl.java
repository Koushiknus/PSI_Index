package com.sp.psi.impl;

import android.content.Context;

import com.sp.psi.controller.PSIController;
import com.sp.psi.interfaces.IAPIListener;
import com.sp.psi.interfaces.IAPIModule;

import java.util.TreeMap;

/**
 * Created by Koushik on 5/26/2018.
 */
public class APIModuleImpl implements IAPIModule {
    protected Context mContext;
    protected TreeMap<String, String> mValues;
    protected IAPIListener iapiListener;

    private static String TAG = APIModuleImpl.class.getSimpleName();
    private PSIController psiController;

    private static APIModuleImpl apiModuleImpl;

    public static IAPIModule getInstance(Context context , IAPIListener iapiListener){

        if(apiModuleImpl == null){
            apiModuleImpl = new APIModuleImpl(context,iapiListener);
        }else{
            apiModuleImpl.rebindListener(context,iapiListener);
        }

        return apiModuleImpl;
    }
    public void rebindListener(Context context, IAPIListener iapiListener) {
        this.mContext = context;
        this.iapiListener = iapiListener;
    }
    private APIModuleImpl(Context context, IAPIListener iapiListener){
        this.mContext = context;
        this.iapiListener = iapiListener;
    }

    @Override
    public void getPSIInformations() {

    }
}
