package com.gerry.simkel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gerry.simkel.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TampilViewAdapter tampilViewAdapter;
    private List<Tampil>data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!Utilities.checkValue(MainActivity.this,"xUserId")){
            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        tampilViewAdapter = new TampilViewAdapter();
        binding.rvTampil.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTampil.setAdapter(tampilViewAdapter);

        tampilViewAdapter.setOnItemLongClickListener(new TampilViewAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, Tampil tampil, int position) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.setGravity(Gravity.RIGHT);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int idMenu = item.getItemId();
                        if (idMenu==R.id.action_edit){
                            Intent intent = new Intent(MainActivity.this, Update_Tampil_Activity.class);
                            intent.putExtra("EXTRA_DATA",tampil);
                            startActivity(intent);
                            return true;
                        } else  if (idMenu==R.id.action_delete){
                            String id = tampil.getId();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Konfirmasi");
                            builder.setMessage("Yakin Ingin Menghapus "+data.get(position).getNamaBarang()+data.get(position).getDesc()+data.get(position).getHarga()+"?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteTampil(id);
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            return true;
                        }else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        binding.fabInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add_Tampil_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteTampil(String id) {
        APIService api = Utilities.getRetrofit().create(APIService.class);
        Call<ValueNoData> call = api.delete_tampil(id);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                if (response.code() == 200) {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        getAllTampil();
                    } else {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                System.out.println("Retrofit Error" + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getAllTampil();
    }

    private void getAllTampil() {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utilities.getRetrofit().create(APIService.class);
        Call<ValueData<List<Tampil>>> call = api.getTampil();
        call.enqueue(new Callback<ValueData<List<Tampil>>>() {
            @Override
            public void onResponse(Call<ValueData<List<Tampil>>> call, Response<ValueData<List<Tampil>>> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        data = response.body().getData();
                        tampilViewAdapter.setData(data);
                    } else {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueData<List<Tampil>>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error :" + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout){
            Utilities.clearUser(this);
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}