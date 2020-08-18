package com.example.comeya;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Rest_reciclerviewadaptor extends RecyclerView.Adapter<Rest_reciclerviewadaptor.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleRest, direccion, telefono;
        private ImageView fotorest;

        public ViewHolder(View itemView){
            super(itemView);
            titleRest=(TextView)itemView.findViewById(R.id.Restviewtitulo);
            direccion=(TextView)itemView.findViewById(R.id.Resyviewdireccion);
            telefono=(TextView)itemView.findViewById(R.id.Restviewphone);
            fotorest=(ImageView) itemView.findViewById(R.id.Restviewimg);
        }
    }
    public List<restView> listaRestview;

    public Rest_reciclerviewadaptor(List<restView>listaRestview){
        this.listaRestview=listaRestview;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_rest,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleRest.setText(listaRestview.get(position).getTitleRest());
        holder.direccion.setText(listaRestview.get(position).getDireccion());
        holder.telefono.setText(listaRestview.get(position).getTelefono());
        holder.fotorest.setImageResource(listaRestview.get(position).getFotorest());
    }

    @Override
    public int getItemCount() {
        return listaRestview.size();
    }
}
