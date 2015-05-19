package com.sean.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by snkelley on 4/14/2015.
 */
public class AlertDialogFragment extends DialogFragment {
    private int mErrorText = R.string.error_message;//default error message

    public void setErrorText(int errorText){
        this.mErrorText = errorText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.error_title))
            .setMessage(context.getString(mErrorText))
            .setPositiveButton(context.getString(R.string.error_ok_button_text), null);
        return builder.create();
    }
}
