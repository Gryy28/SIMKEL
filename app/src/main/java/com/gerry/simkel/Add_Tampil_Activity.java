package com.gerry.simkel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.gerry.simkel.databinding.ActivityAddTampilBinding;
import com.gerry.simkel.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Tampil_Activity extends AppCompatActivity {
    private ActivityAddTampilBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTampilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = binding.etNamabarang.getText().toString();
                int harga = Integer.parseInt(binding.etHarga.getText().toString());
                String desc = binding.etDesc.getText().toString();

                boolean bolehTampil = true;
                if (TextUtils.isEmpty(namaBarang)){
                    bolehTampil =false;
                    binding.etNamabarang.setError("Nama Barang Tidak Boleh Kosong");
                }
                if (harga<=0){
                    bolehTampil =false;
                    binding.etNamabarang.setError("Harga Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(desc)){
                    bolehTampil =false;
                    binding.etNamabarang.setError("Deskripsi Tidak Boleh Kosong");
                }
                if (bolehTampil){
                    String userId = Utilities.getValue(Add_Tampil_Activity.this,"xUserId");
                    addTampil(userId,namaBarang,harga,desc);
                }
            }
        });
    }

    private void addTampil(String userId, String namaBarang, int harga, String desc) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utilities.getRetrofit().create(APIService.class);
        Call<ValueNoData> call = api.add_tampil(namaBarang,harga,desc,userId);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1) {
                        Toast.makeText(Add_Tampil_Activity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Add_Tampil_Activity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Add_Tampil_Activity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error :" + t.getMessage());
                Toast.makeText(Add_Tampil_Activity.this, "Retrofit Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}