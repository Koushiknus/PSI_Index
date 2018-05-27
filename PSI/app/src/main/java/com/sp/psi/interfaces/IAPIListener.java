package com.sp.psi.interfaces;

import com.sp.psi.entity.PSIInfo;

/**
 * Created by Koushik on 5/26/2018.
 */
public interface IAPIListener {

    public void onGetPSIInformationDone(boolean success,String message,PSIInfo psiInfo);

}
