package com.example.dirtychickenapp.pantallas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.Producto;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Producto> mData;
    private LayoutInflater mInflater;
    private Context context;

    public Adapter(List<Producto> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = mData.get(position);

        Glide.with(context)
                .load(producto.getRuta_imagen())
                .into(holder.imgView);
        holder.txtProducto.setText(producto.getNombre_producto());
        holder.txtPrecio.setText(String.valueOf(producto.getPrecio()));

        holder.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Log.d("Adapter", "Botón clickeado en posición: " + adapterPosition);

                    Producto producto = mData.get(adapterPosition);
                    String nombreProducto = producto.getNombre_producto();
                    Log.d("Adapter", "Nombre del producto: " + nombreProducto);

                    // Puedes realizar acciones adicionales aquí con el producto en esta posición
                }
            }
        });
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
        Button btnAgregar;

        ViewHolder(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgView);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            btnAgregar = itemView.findViewById(R.id.btnAgregar);
        }
    }
}
