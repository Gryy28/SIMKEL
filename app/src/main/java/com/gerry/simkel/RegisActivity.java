package com.gerry.simkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.gerry.simkel.databinding.ActivityRegisBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisActivity extends AppCompatActivity {

    private ActivityRegisBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                String konfirmasiPassword = binding.etKonfirmasiPassword.getText().toString();

                boolean bolehRegister = true;
                if (TextUtils.isEmpty(username)){
                    bolehRegister = false;
                    binding.etUsername.setError("Username Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(password)){
                    bolehRegister = false;
                    binding.etPassword.setError("Password Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(konfirmasiPassword)){
                    bolehRegister = false;
                    binding.etKonfirmasiPassword.setError("Konfirmasi Password Tidak Boleh Kosong");
                }
                if (!password.equals(konfirmasiPassword)){
                    bolehRegister = false;
                    binding.etKonfirmasiPassword.setError("Konfirmasi Password Tidak Sama Dengan Password");
                }
                if (password.length()<6){
                    bolehRegister=false;
                    binding.etPassword.setError("Password Minimal 6 Karakter");
                }
                if (bolehRegister){
                    register(username,password);
                }
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void register(String username, String password) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utilities.getRetrofit().create(APIService.class);
        Call<ValueData<User>> call = api.register(username,password);
        call.enqueue(new Callback<ValueData<User>>() {
            @Override
            public void onResponse(Call<ValueData<User>> call, Response<ValueData<User>> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1){
                        User user = response.body().getData();
                        Toast.makeText(RegisActivity.this, message, Toast.LENGTH_SHORT).show();
                        Utilities.setValue(RegisActivity.this,"xUserId",user.getId());
                        Intent intent = new Intent(RegisActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisActivity.this, message, Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(RegisActivity.this, "Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueData<User>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error :"+ t.getMessage());
                Toast.makeText(RegisActivity.this, "Retrofit Error", Toast.LENGTH_SHORT).show();

            }
        });



    }
}

