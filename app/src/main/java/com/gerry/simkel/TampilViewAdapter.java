package com.gerry.simkel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerry.simkel.databinding.ItemTampilBinding;

import java.util.ArrayList;
import java.util.List;

public class TampilViewAdapter extends RecyclerView.Adapter<TampilViewAdapter.ViewHolder> {
    private List<Tampil> data = new ArrayList<>();
    private onItemLongClickListener onItemLongClickListener;
    public void setData(List<Tampil>data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(TampilViewAdapter.onItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }

    @NonNull
    @Override
    public TampilViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTampilBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TampilViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Tampil tampil = data.get(pos);
        holder.itemTampilBinding.tvNamabarang.setText(tampil.getNamaBarang());
        holder.itemTampilBinding.tvDesc.setText(tampil.getDesc());
        holder.itemTampilBinding.tvHarga.setText(String.valueOf(tampil.getHarga()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClickListener(v,tampil,pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemTampilBinding itemTampilBinding ;
        public ViewHolder(@NonNull ItemTampilBinding itemView) {
            super(itemView.getRoot());
            itemTampilBinding=itemView;
        }
    }

    public interface onItemLongClickListener{
        void onItemLongClickListener(View v,Tampil tampil,int position);
    }
}
