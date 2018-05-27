package com.sp.psi.gateway.asynctasks;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sp.psi.R;
import com.sp.psi.entity.PSIInfo;
import com.sp.psi.gateway.APIGateway;
import com.sp.psi.gateway.json.APIService;
import com.sp.psi.impl.PSIEntityFactory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.TreeMap;

/**
 * Created by Koushik on 5/27/2018.
 */
public class FetchInformationAsynctask extends BaseAsyncTask {


    public FetchInformationAsynctask(Context context, Handler handler, TreeMap<String,String> values){

        this.msg.what = APIGateway.FETCH_INFO;
        this.mHandler = handler;
        this.mContext = context;
        this.mInputData = values;
    }

    @Override
    protected Message doInBackground(Object... params) {

        PSIInfo psiInfo = PSIEntityFactory.getPsiInfo();
        String result = "";
        try{
            sender =  new APIService.APIRequestSender(mContext);
            result = sender.sendGetPSIInformationRequest(mContext,mInputData);
            respReader = new APIService.JsonResponseReader(result);

            TreeMap<String,String> data = respReader.getData();
            psiInfo.setData(data);


        }catch (ConnectException ce) {
            ce.printStackTrace();
            return createMessage(getString(R.string.network_error), ce.getMessage());
        }  catch (SocketTimeoutException ste) {
            ste.printStackTrace();
            return createMessage(getString(R.string.server_timeout), ste.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return createMessage(getString(R.string.error), e.getMessage());
        }

        return createMessage();

    }

    @Override
    protected void onPostExecute(Message message) {
        this.mHandler.sendMessage(message);
        super.onPostExecute(message);
    }

}
