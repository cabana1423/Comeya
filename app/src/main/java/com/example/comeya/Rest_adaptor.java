package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Rest_adaptor extends RecyclerView.Adapter<Rest_adaptor.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleRest, direccion, telefono;
        private ImageView fotorest;
        private CardView vista;
        Context context;

        public ViewHolder(View itemView){
            super(itemView);
            context=itemView.getContext();
            titleRest=(TextView)itemView.findViewById(R.id.Restviewtitulo);
            direccion=(TextView)itemView.findViewById(R.id.Resyviewdireccion);
            telefono=(TextView)itemView.findViewById(R.id.Restviewphone);
            fotorest=(ImageView) itemView.findViewById(R.id.Restviewimg);
            vista=(CardView) itemView.findViewById(R.id.Restgroupview);

        }
        void setOnclickCardview(){
            vista.setOnClickListener(this);
        };

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Restgroupview:
                    Intent intent=new Intent(context,realizar_pedido.class);
                    context.startActivity(intent);
                    break;
            }
        }

    }
    public List<restView> listaRestview;

    public Rest_adaptor(List<restView>listaRestview){
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
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
        return listaRestview.size();
    }
}
