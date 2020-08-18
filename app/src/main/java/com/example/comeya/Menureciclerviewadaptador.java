package com.example.comeya;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Menureciclerviewadaptador extends RecyclerView.Adapter<Menureciclerviewadaptador.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
            private TextView restaurant, precio, descripcion;
            private ImageView fotomenu;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurant=(TextView)itemView.findViewById(R.id.nobreRestList);
            precio=(TextView)itemView.findViewById(R.id.precioMenulist);
            descripcion=(TextView)itemView.findViewById(R.id.descripcionlistmenu);
            fotomenu=(ImageView)itemView.findViewById(R.id.imgviewmenu);


        }
    }
    public List<menuView> listamenuview;

        public Menureciclerviewadaptador(List<menuView> listamenuview) {
        this.listamenuview = listamenuview;
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
        holder.restaurant.setText(listamenuview.get(position).getRestaurant());
        holder.precio.setText(listamenuview.get(position).getPrecio());
        holder.descripcion.setText(listamenuview.get(position).getDescripcion());
        holder.fotomenu.setImageResource(listamenuview.get(position).getFotomenu());
    }

    @Override
    public int getItemCount() {
        return listamenuview.size();
    }


}
