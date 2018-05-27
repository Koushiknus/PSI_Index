package com.sp.psi.gateway.json;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sp.psi.gateway.asynctasks.JSONRequestsender;
import com.sp.psi.gateway.asynctasks.JSONResponseReader;
import com.sp.psi.utils.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Koushik on 5/27/2018.
 */
public class APIService {



    private static ArrayList<JsonObject> mResultJsonArrayObjects = new ArrayList<JsonObject>();

    private static Gson gson = new Gson();

    private static final String PSI_SERVICE_API = "https://api.data.gov.sg/v1/environment/psi"; // Live Environment

    private static HashMap<String, String> mHeaderData;


    public static class APIRequestSender extends JSONRequestsender {

        public APIRequestSender(Context context) {

        }

        public static HashMap<String, String> getHeaders() {
            return mHeaderData;
        }

        private String sendRequest(Context ctx,String endpoint,TreeMap<String, String> inputData){

            OkHttpClient client = new OkHttpClient();
            MultipartBody.Builder builder =new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (Map.Entry<String,String> entry : inputData.entrySet()) {
                String keyValue = (String) entry.getKey();
                String value = (String) entry.getValue();
                builder.addFormDataPart(keyValue, value);
            }

            String headers = setHeaders(ctx);
            RequestBody requestBody = null;
            requestBody = builder.build();


            Log.i("EndPoint",PSI_SERVICE_API);
            String bearer = "Bearer ";
            Request testRequest = new Request.Builder()
                    .url(PSI_SERVICE_API)
                    .addHeader("Authorization",bearer+ headers)
                    .post(requestBody)

                    .build();
            Log.i("RequestPacket",bodyToString(testRequest));
            Log.i("RequestBody",testRequest.body().toString());


            Headers responseHeaders = testRequest.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.v("RequestHeaders",responseHeaders.name(i)+ responseHeaders.value(i));
            }


            String jsonData = null;
            try{
                Response response = client.newCall(testRequest).execute();
                jsonData = response.body().string();
                Log.v("JsonData",jsonData);
            }catch (Exception e){
                e.printStackTrace();
            }

            return jsonData;
        }


        private String setHeaders(Context ctx){

           return "";
        }


        private static String bodyToString(final Request request){

          /*  try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }*/
            return "";

        }

        @Override
        public String sendGetPSIInformationRequest(Context context, TreeMap<String, String> inputData) throws Exception {
            return null;
        }
    }


    public static class JsonResponseReader  extends JSONResponseReader{
        private static final String TAG = JsonResponseReader.class.getSimpleName();
        private String mResponseCode;
        private String mErrorMessage;
        private TreeMap<String, String> mData;


        public JsonResponseReader(String result) {

            mTransList = new HashMap<String, Object>();


            if (result == null) {

            }
            JsonParser parser = new JsonParser();
            JsonElement e = parser.parse(result);
            //JSONObject responseData = e.getAsJsonObject();// get complete retuned data as JSON Object
            JsonObject responseData = e.getAsJsonObject();
            if (responseData != null) {
                mData = new TreeMap();
                parseData(responseData);
            }else{
                mErrorMessage = "Unexpected Json Response";
            }

            Log.i(TAG, "responseJson=" + result);

        }


        private boolean parseHeader(JsonObject responseHeader) {
            if (responseHeader != null && responseHeader.has(Const.RESPONSE_CODE)) {
                JsonElement responseCode = responseHeader.get(Const.RESPONSE_CODE);
                mResponseCode = responseCode.getAsString();
                if ("0000".equals(mResponseCode)) {
                    return true;
                }else if (responseHeader.has(Const.ERROR_MESSAGE)) {
                    JsonElement errorMessage = responseHeader.get(Const.ERROR_MESSAGE);
                    mErrorMessage = errorMessage.getAsString();
                }else{
                    mErrorMessage = "Unexpected Json Response";
                }
            }else{
                mErrorMessage = "Unexpected Json Response";
            }
            return false;
        }

        private boolean parseData(JsonObject responseData){
            if (responseData != null){
                if (responseData.has(Const.DATA)){
                    com.google.gson.JsonObject obj = responseData.getAsJsonObject(Const.DATA);
                    if(obj.get("transactions")!=null){
                        parseToTransList(obj);
                    }else{
                        parseToData(responseData);
                    }

                }else{
                    parseToData(responseData);
                    //convertAmountToDollar();
                }
                return true;
            }
            return false;
        }


        private void parseToData(JsonObject responseData) {
            Iterator<Map.Entry<String, JsonElement>> iterator = responseData.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, JsonElement> pair = iterator.next();
                String key = pair.getKey();
                JsonElement value = pair.getValue();
                if (value.isJsonObject()) {
                    String jsonString = gson.toJson(value);
                    Log.i(TAG, "json=" + jsonString);
                    mData.put(key, jsonString);
                }
                else if (key != null && value != null && !value.isJsonNull()&&!value.isJsonArray()){
                    Log.i(key, value.getAsString());
                    mData.put(key, value.getAsString());
                }
                /*
                        If the Value is Json Array ,the following block gets executed
                        Added for Collection of lists in the response
                 */
                else if(value.isJsonArray()){
                    JsonArray jsonArray = value.getAsJsonArray();
                    ArrayList<JsonObject> mJsonArrayObjects = new ArrayList<JsonObject>();

                    for(int i=0;i<jsonArray.size();i++){
                        JsonObject childJsonObject = (JsonObject)jsonArray.get(i);
                        mJsonArrayObjects.add(childJsonObject);

                    }
                    mResultJsonArrayObjects = mJsonArrayObjects;

                }
            }
            Log.v("AfterParse",mData.toString());
        }

        public String getResponseCode() {
            return mResponseCode;
        }

        public String getErrorMessage() {
            return mErrorMessage;
        }

        public TreeMap<String, String> getData() {
            return mData;
        }
        /*
            Reyurns list of JSON Array Objects
         */
        public ArrayList<JsonObject> getJsonArrayObjects(){
            return mResultJsonArrayObjects;
        }
        private void parseToTransList(JsonObject responseData){
            Log.v("InsideTLList","InsideTLList");
            Iterator<Map.Entry<String, JsonElement>> iter = responseData.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String, JsonElement> pair = iter.next();
                String key = pair.getKey();
                Log.v("KeyIs",key);
                JsonElement value = pair.getValue();
                if (key != null && value != null && !value.isJsonNull()){
                    if (Const.ITEMS.equals(key)){
                        Log.v("BeforeAdditionList","BeforeAdditionList");
                        mTransList.put(Const.ITEMS, parseLoopData(value.getAsJsonArray()));
                        Log.v("ListSize",Integer.toString(mTransList.size()));
                    }else{
                        Log.i(key, value.getAsString());
                        mTransList.put(key, value.getAsString());
                    }
                }
            }
        }
        private ArrayList<TreeMap<String, String>> parseLoopData(JsonArray loopData){
            ArrayList<TreeMap<String, String>> transList = null;
            if (loopData != null && loopData.size() > 0){
                transList = new ArrayList<TreeMap<String,String>>();
                for (int i = 0; i < loopData.size(); i++){
                    JsonObject txnObject = loopData.get(i).getAsJsonObject();
                    Iterator<Map.Entry<String, JsonElement>> iter = txnObject.entrySet().iterator();
                    TreeMap<String, String> txn = new TreeMap<String, String>();
                    while(iter.hasNext()){
                        Map.Entry<String, JsonElement> pair = iter.next();
                        String key = pair.getKey();
                        JsonElement value = pair.getValue();
                        if (key != null && value != null && !value.isJsonNull()){
                            Log.i(key, value.getAsString());
                            Log.v("PairKey",pair.getKey());
                            Log.v("PairValue",pair.getValue().getAsString());
                            txn.put(pair.getKey(), pair.getValue().getAsString());
                        }
                    }

                    transList.add(txn);
                }
            }
            return transList;
        }

        @Override
        public HashMap<String, Object> getTransList() {
            return mTransList;
        }
        @Override
        public String getResCode() {
            return mResponseCode;
        }



    }
}
