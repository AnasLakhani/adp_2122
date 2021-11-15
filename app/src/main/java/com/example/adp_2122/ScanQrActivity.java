package com.example.adp_2122;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.adp_2122.databinding.ActivityScanQrBinding;

public class ScanQrActivity extends AppCompatActivity {

    ActivityScanQrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains("\n")) {
                    binding.scanQr.setText("");
                    startActivity(new Intent(ScanQrActivity.this,MainActivity.class));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        binding.scanQr.addTextChangedListener(textWatcher);

    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.scanQr.requestFocus();
    }
}