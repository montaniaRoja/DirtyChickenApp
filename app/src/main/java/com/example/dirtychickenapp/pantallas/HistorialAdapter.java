package com.example.dirtychickenapp.pantallas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.PedidosEstado;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private Context Hctx;
    private List<PedidosEstado> pedidosEstadoList;


    public HistorialAdapter(Context Hctx, List<PedidosEstado> pedidosEstadoList){
        this.Hctx=Hctx;
        this.pedidosEstadoList=pedidosEstadoList;

    }

    @Override
    public HistorialViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        LayoutInflater inflater=LayoutInflater.from(Hctx);
        View view=inflater.inflate(R.layout.list_element,null);
        return new HistorialViewHolder(view);

    }

    @Override
    public void onBindViewHolder(HistorialViewHolder holder, int posicion){
        PedidosEstado pedidosEstado=pedidosEstadoList.get(posicion);

        int resourceId = Hctx.getResources().getIdentifier("polloverde", "drawable", Hctx.getPackageName());

        Glide.with(Hctx)
                .load(resourceId)
                .into(holder.imageView5);
        holder.txtEstado.setText(pedidosEstado.getEstado());
        holder.txtMonto.setText(String.valueOf(pedidosEstado.getTotal()));


    }

    @Override
    public int getItemCount(){return pedidosEstadoList.size();}

    class HistorialViewHolder extends RecyclerView.ViewHolder{
        TextView txtMonto;
        TextView txtEstado;
        ImageView imageView5;
        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEstado=itemView.findViewById(R.id.txtEstado);
            txtMonto=itemView.findViewById(R.id.txtMonto);
            imageView5=itemView.findViewById(R.id.imageView5);
            txtEstado.setEnabled(false);
            txtEstado.setKeyListener(null);
            txtEstado.setFocusable(false);
            txtEstado.setClickable(false);
            txtEstado.setLongClickable(false);

            txtMonto.setEnabled(false);
            txtMonto.setKeyListener(null);
            txtMonto.setFocusable(false);
            txtMonto.setClickable(false);
            txtMonto.setLongClickable(false);

        }
    }


}
