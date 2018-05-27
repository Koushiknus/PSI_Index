package com.sp.psi.entity;

import java.util.TreeMap;

/**
 * Created by Koushik on 5/26/2018.
 */
public class PSIInfo {

    private static TreeMap<String,String> fetchUserInfo;

    private static void intializeUserInfo(){

        if(fetchUserInfo == null){
            fetchUserInfo = new TreeMap<String, String>();
        }
    }

    public static void setData(TreeMap<String,String> inputValues){
        intializeUserInfo();
/*
        if(inputValues.containsKey(Const.SUCCESS)){

            fetchUserInfo.put(MGXConst.SUCCESS,inputValues.get(MGXConst.SUCCESS));

        }
        if(inputValues.containsKey(MGXConst.STATUS)){
            fetchUserInfo.put(MGXConst.STATUS,(String)inputValues.get(MGXConst.STATUS));
        }
        if(inputValues.containsKey(MGXConst.EMAIL)){
            fetchUserInfo.put(MGXConst.EMAIL,inputValues.get(MGXConst.EMAIL));
        }

        if(inputValues.containsKey(MGXConst.DATA)){

            try{
                org.json.JSONObject data = new org.json.JSONObject(inputValues.get(MGXConst.DATA));

                if(data.getString(MGXConst.EMAIL)!=null){
                    fetchUserInfo.put(MGXConst.EMAIL,data.getString(MGXConst.EMAIL));

                }

                String merchant = data.getString(MGXConst.MERCHANT);
                org.json.JSONObject merchantDetails = new org.json.JSONObject(merchant);
                if(merchantDetails.get(MGXConst.SHORT_NAME)!=null){
                    fetchUserInfo.put(MGXConst.SHORT_NAME,(String) merchantDetails.get(MGXConst.SHORT_NAME));
                }

                if(merchantDetails.get(MGXConst.BANK_INFO_FILLED)!=null){
                    fetchUserInfo.put(MGXConst.BANK_INFO_FILLED,String.valueOf (merchantDetails.get(MGXConst.BANK_INFO_FILLED)) );

                }
                if(merchantDetails.get(MGXConst.FULL_NAME)!=null){

                    fetchUserInfo.put(MGXConst.FULL_NAME,(String)merchantDetails.get(MGXConst.FULL_NAME));
                }
                if(merchantDetails.get(MGXConst.BANK_NAME)!=null){
                    fetchUserInfo.put(MGXConst.BANK_NAME,(String)merchantDetails.get(MGXConst.BANK_NAME));
                }
                if(merchantDetails.get(MGXConst.BANK_ACCOUNT_NUMBER)!=null){
                    fetchUserInfo.put(MGXConst.BANK_ACCOUNT_NUMBER,(String)merchantDetails.get(MGXConst.BANK_ACCOUNT_NUMBER));
                }
                if(merchantDetails.get(MGXConst.REG_COMPANY)!=null){
                    fetchUserInfo.put(MGXConst.REG_COMPANY,(String)merchantDetails.get(MGXConst.REG_COMPANY));
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }*/

    }


}
