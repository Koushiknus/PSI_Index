package com.sp.psi.gateway.asynctasks;

import android.content.Context;

import java.util.TreeMap;

/**
 * Created by Koushik on 5/27/2018.
 */
public abstract class JSONRequestsender {

    abstract public String sendGetPSIInformationRequest(Context context, TreeMap<String, String> inputData)throws Exception;
}
