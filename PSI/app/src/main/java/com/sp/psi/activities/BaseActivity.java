package com.sp.psi.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import com.sp.psi.R;
import com.sp.psi.utils.AppVariables;

public class BaseActivity extends Activity {

    protected Handler mUIThreadHandler = new Handler();
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateOrientation();
    }
    protected void showProgressDialog(final int msgId) {
        showProgressDialog(getString(msgId));
    }

    protected void showProgressDialog(final String msg){
        showProgressDialog(msg, false);
    }

    protected void showProgressDialog(final String msg, final boolean cancelable) {
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null){
                    mProgressDialog = new ProgressDialog(BaseActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
                }
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMessage(msg);
                mProgressDialog.setCancelable(cancelable);
                mProgressDialog.show();

            }

        });
    }

    protected void stopProgressDialog(){
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mProgressDialog = null;
                }
            }
        });
    }

    protected void showAlertDialog(int titleId, int messageId) {
        showAlertDialog(getString(titleId), getString(messageId));
    }

    protected void showAlertDialog(String title, String message) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        };
        showAlertDialog(title, message, listener);
    }

    protected void showAlertDialog(int titleId, int messageId, DialogInterface.OnClickListener listener) {
        showAlertDialog(getString(titleId), getString(messageId), listener);
    }

    protected void showAlertDialog(final String title, final String message, final DialogInterface.OnClickListener listener) {
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder ab = new AlertDialog.Builder(BaseActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                ab.setTitle(title);
                ab.setMessage(message);
                ab.setPositiveButton(getString(R.string.ok), listener);
                ab.setCancelable(false);
                ab.show();
                // CustomDialogClass cdd = new CustomDialogClass(BaseActivity.this,title,message);
                //cdd.show();


            }
        });


    }

    protected void showQuitDialog(int titleId, int messageId) {
        showQuitDialog(getString(titleId), getString(messageId));
    }

    protected void showQuitDialog(String title, String message) {
        showQuitDialog(title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    protected void showQuitDialog(int titleId, int messageId, DialogInterface.OnClickListener listener) {
        showQuitDialog(getString(titleId), getString(messageId), listener);
    }

    protected void showQuitDialog(final String title, final String message, final DialogInterface.OnClickListener listener) {
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(BaseActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                dialog.setTitle(title);
                dialog.setMessage(message);
                dialog.setCancelable(false);
                dialog.setPositiveButton(getString(R.string.ok), listener);
                dialog.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
                dialog.show();
            }
        });
    }

    protected void updateOrientation() {
        int orientation = AppVariables.getOrientationSetting(this);
        updateOrientation(orientation);
    }





    protected void updateOrientation(int orientation) {
        switch (orientation) {
            case AppVariables.ORIENTATION_PORT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case AppVariables.ORIENTATION_LAND:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
    }

    protected String toLogMsg(int num) {
        return "[" + String.valueOf(num) + "]";
    }

    protected String toLogMsg(boolean b) {
        return "[" + String.valueOf(b) + "]";
    }

    protected String toLogMsg(String s) {
        return "[" + s + "]";
    }
}
