package com.sp.psi.gateway.asynctasks;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Koushik on 5/27/2018.
 */
public abstract class JSONResponseReader {

    protected String mResponseCode;
    protected String mResponseType;
    protected String mErrorMessage;
    protected TreeMap<String, String> mData;
    protected HashMap<String, Object> mTransList;

    abstract public String getResCode();
    abstract public String getErrorMessage();
    abstract public TreeMap<String, String> getData();
    abstract public HashMap<String, Object> getTransList();
}
