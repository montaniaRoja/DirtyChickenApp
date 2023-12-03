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
import com.example.dirtychickenapp.objetos.DetallePedido;
import com.example.dirtychickenapp.objetos.Producto;
import com.example.dirtychickenapp.objetos.TotalPedido;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Producto> mData;
    private static List<DetallePedido> detallesPedido = new ArrayList<>(); // Lista para almacenar detalles del pedido
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener mListener;
    private double acumulado=0;
    public int productCount=0;
    private TotalPedido totalPedido;
    public Adapter(List<Producto> itemList, Context context, OnItemClickListener listener, TotalPedido totalPedido) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.mListener = listener;
        this.totalPedido = totalPedido;
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
                productCount += 1;
                int adapterPosition = holder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Log.d("Adapter", "Botón clickeado en posición: " + adapterPosition);
                    mListener.onItemClick(adapterPosition, productCount);
                    Producto producto = mData.get(adapterPosition);
                    Log.d("detalle pedido size", "detalle pedido size " + String.valueOf(productCount));
                    int posicion = adapterPosition;
                    String nombreProducto = producto.getNombre_producto();
                    Double precioProducto = producto.getPrecio();
                    int cantidad = 1;
                    DetallePedido detalleExistente = null;

                    for (DetallePedido detalle : detallesPedido) {
                        if (detalle.getPosicion() == posicion) {
                            detalleExistente = detalle;
                            break;
                        }
                    }

                    if (detalleExistente != null) {
                        detalleExistente.setCantidad(detalleExistente.getCantidad() + 1);
                    } else {
                        DetallePedido detallePedido = new DetallePedido(posicion, nombreProducto, precioProducto, cantidad);
                        detallesPedido.add(detallePedido);
                    }

                    Log.d("Adapter", "Nombre del producto: " + nombreProducto);


                    acumulado = 0;
                    for (DetallePedido detalle : detallesPedido) {
                        acumulado = acumulado + (detalle.getCantidad() * detalle.getPrecioProducto());
                        Log.d("Adapter", "Detalle del pedido: " + detalle.getNombreProducto() + ", Precio " + detalle.getPrecioProducto() + ", Cantidad: " + detalle.getCantidad());
                    }

                    totalPedido.setTotal(String.valueOf(acumulado));

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
    public static List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }
    public static void clearDetallesPedido() {
        detallesPedido.clear();
    }
}
