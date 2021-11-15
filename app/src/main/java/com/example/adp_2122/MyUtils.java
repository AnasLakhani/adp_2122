package com.example.adp_2122;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import com.example.adp_2122.databinding.ProgressLoadingDialogBinding;

public class MyUtils {

    public static AlertDialog getLoadingDialog(Activity context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        ProgressLoadingDialogBinding binding = ProgressLoadingDialogBinding.inflate(context.getLayoutInflater());
        dialogBuilder.setView(binding.getRoot());
        dialogBuilder.setCancelable(false);
        return dialogBuilder.create();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
