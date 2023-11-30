package com.example.dirtychickenapp.database;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.Producto;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Producto> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<Producto> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<Producto> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView txtPrecio, txtProducto;

        ViewHolder(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgView);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtProducto = itemView.findViewById(R.id.txtProducto);
        }

        void bindData(final Producto item) {
            Log.d("ListAdapter", "bindData llamado para: " + item.getNombre_producto());  // Agrega este log

            txtProducto.setText(item.getNombre_producto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));

            // Usar Glide para cargar imágenes
            Glide.with(itemView.getContext())
                    .load(item.getRuta_imagen()).into(imgView);

            Log.d("ListAdapter", "Producto: " + item.getNombre_producto() + ", Precio: " + item.getPrecio() + ", Ruta: " + item.getRuta_imagen());


        }
    }
}
