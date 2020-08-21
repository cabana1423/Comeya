package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Menu_adapter extends RecyclerView.Adapter<Menu_adapter.ViewHolder>{


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //variable context para acceder a otra activity
        Context context;
        private TextView titleRest, precio, descripcion;
        private ImageView fotoMenu;
        private Button btverrest, btpedido;

        public ViewHolder(View itemView){
            super(itemView);
            //acceder a activity
            context=itemView.getContext();
            titleRest=(TextView)itemView.findViewById(R.id.Menuview_titleRest);
            precio =(TextView)itemView.findViewById(R.id.Menuview_precio);
            descripcion =(TextView)itemView.findViewById(R.id.Menuview_descrip);
            fotoMenu =(ImageView) itemView.findViewById(R.id.imageViewMenu);
            btverrest =(Button) itemView.findViewById(R.id.botMenuview_verRest);
            btpedido =(Button) itemView.findViewById(R.id.botMenuview_hacerpedido);
        }

        void setOnClickListeners(){
            btpedido.setOnClickListener(this);
            btverrest.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.botMenuview_hacerpedido:
                    Intent intent=new Intent(context,realizar_pedido.class);
                    context.startActivity(intent);
                    break;
                case R.id.botMenuview_verRest:
                    Intent intent2=new Intent(context,realizar_pedido.class);
                    context.startActivity(intent2);
                    break;
            }
        }
    }
    public List<Menuview> listaMenu;

    public Menu_adapter(List<Menuview>listaMenu){
        this.listaMenu =listaMenu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_menu,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleRest.setText(listaMenu.get(position).getTitleRest());
        holder.precio.setText(listaMenu.get(position).getPrecio());
        holder.descripcion.setText(listaMenu.get(position).getDescripcion());
        holder.fotoMenu.setImageResource(listaMenu.get(position).getFotomenu());
        //eventos
        holder.setOnClickListeners();
    }
    @Override
    public int getItemCount() {
        return listaMenu.size();
    }
}
