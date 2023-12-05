package com.example.dirtychickenapp.pantallas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.DetallePedido;

import java.util.List;

public class PedidoAdapter extends ArrayAdapter<DetallePedido> {
    public PedidoAdapter(Context context, List<DetallePedido> detallesPedido) {
        super(context, 0, detallesPedido);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetallePedido detalle = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pedido_personalizado, parent, false);
        }

        TextView txtNombre = convertView.findViewById(R.id.txtNombreP);
        TextView txtPrecioUnitario = convertView.findViewById(R.id.txtPrecioUnitario);
        TextView txtCantidad = convertView.findViewById(R.id.txtCantidad);
        TextView txtPrecioTotal = convertView.findViewById(R.id.txtPrecioTotal);

        // Actualiza las vistas con los datos del DetallePedido actual
        txtNombre.setText(detalle.getNombreProducto());
        txtPrecioUnitario.setText(String.valueOf(detalle.getPrecioProducto()));
        txtCantidad.setText(String.valueOf(detalle.getCantidad()));
        txtPrecioTotal.setText(String.valueOf(detalle.getPrecioProducto() * detalle.getCantidad()));

        return convertView;
    }
}