package com.sp.psi.impl;

import com.sp.psi.entity.PSIInfo;

/**
 * Created by Koushik on 5/27/2018.
 */
public class PSIEntityFactory {

    private static PSIInfo psiInfo;


    public static PSIInfo getPsiInfo() {

        if(psiInfo == null){
            psiInfo = new PSIInfo();
        }
        return  psiInfo;
    }
}
