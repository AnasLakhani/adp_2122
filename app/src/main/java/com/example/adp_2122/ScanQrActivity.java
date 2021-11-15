package com.example.adp_2122;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.adp_2122.databinding.ActivityManualBinding;
import com.example.adp_2122.databinding.ActivityScanQrBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ActivityScanQrBinding binding;

    BottomSheetDialog mBottomSheetDialog;
    AlertDialog loading_dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(this,
                            new String[]{(Manifest.permission.CAMERA)},
                            101);
                } else {
                    binding.zxscan.startCamera();
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loading_dialog = MyUtils.getLoadingDialog(this);
        mBottomSheetDialog = new BottomSheetDialog(this);

        binding.back.setOnClickListener(view -> qrManually());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                        new String[]{(Manifest.permission.CAMERA)},
                        101);
            } else{
                binding.zxscan.startCamera();
            }


        }


    }

    private void qrManually() {
        binding.zxscan.stopCamera();

        ActivityManualBinding a_binding = ActivityManualBinding.inflate(this.getLayoutInflater());

        a_binding.rescan.setOnClickListener(view2 -> onResume());
        a_binding.verify.setOnClickListener(view3 -> {

            if (String.valueOf(a_binding.codeManuallyEdit.getText()).isEmpty()) {
                a_binding.codeManuallyEdit.setError("Enter code");
                a_binding.codeManuallyEdit.requestFocus();
                return;
            }

            MyUtils.hideKeyboard(this);

            ExecuteQRDetail(String.valueOf(a_binding.codeManuallyEdit.getText()));
        });


        mBottomSheetDialog.setContentView(a_binding.getRoot());
        mBottomSheetDialog.show();

    }

    private void ExecuteQRDetail(String valueOf) {

        loading_dialog.show();


        new Handler().postDelayed(() -> {
            loading_dialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("barcode", valueOf);
            startActivity(intent);

        }, 3000);

    }

    private static final String TAG = "ScanActivity";

    @Override
    public void handleResult(Result result) {
        String SerialNo = result.getText();
        Log.d(TAG, "handleResult: " + SerialNo);
        ExecuteQRDetail(String.valueOf(result.getText()));
    }

    @Override
    protected void onPause() {
        super.onPause();

        binding.zxscan.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomSheetDialog.dismiss();
        binding.zxscan.setResultHandler(this);
        binding.zxscan.startCamera();
    }
}