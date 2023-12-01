package com.example.dirtychickenapp.pantallas;

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
import com.bumptech.glide.annotation.GlideModule;
import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.Producto;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private String[] mDataset;
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
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto=mData.get(position);
        //holder.bindData(mData.get(position));
        Glide.with(context)
                .load(producto.getRuta_imagen())
                .into(holder.imgView);
        holder.txtProducto.setText(producto.getNombre_producto());
        holder.txtPrecio.setText(String.valueOf(producto.getPrecio()));



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
        /*
        void bindData(final Producto item) {
                if (item == null) {
                Log.d("ListAdapter", "bindData llamado con Producto nulo");
                // Puedes manejar el caso de Producto nulo de alguna manera, por ejemplo, ocultar la vista o configurar datos de marcador de posición.
                Glide.with(itemView.getContext()).load(R.drawable.polloverde).into(imgView);
                txtProducto.setText("Producto no disponible");
                txtPrecio.setText("");
                // O puedes ocultar la ImageView
                imgView.setVisibility(View.GONE);
                return; // Salir del método si el producto es nulo
            }

            Log.d("ListAdapter", "bindData llamado para: " + item.getNombre_producto());

            txtProducto.setText(item.getNombre_producto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));

            // Verificar que la ruta de la imagen no sea nula o vacía antes de cargar con Glide
            if (item.getRuta_imagen() != null && !item.getRuta_imagen().isEmpty()) {
                // Usar Glide para cargar imágenes
                Glide.with(imgView.getContext())
                        .load(item.getRuta_imagen()).into(imgView);
            } else {
                // Si la ruta de la imagen es nula o vacía, puedes manejarlo de alguna manera, por ejemplo, cargar una imagen de marcador de posición.
                Glide.with(itemView.getContext()).load(R.drawable.polloverde).into(imgView);
                // O puedes ocultar la ImageView
                //imgView.setVisibility(View.GONE);
            }

            Log.d("ListAdapter", "Producto: " + item.getNombre_producto() + ", Precio: " + item.getPrecio() + ", Ruta: " + item.getRuta_imagen());
        }
*/

    }



}
