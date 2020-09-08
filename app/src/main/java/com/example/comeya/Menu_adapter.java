package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Menu_adapter extends RecyclerView.Adapter<Menu_adapter.Holdermenu>{
    Context context;
    ArrayList<Menuview> lista_menu;
    Menu_adapter(Context context){
        this.context=context;
        lista_menu=new ArrayList<>();
    }
    void add(Menuview menu){
        lista_menu.add(menu);
        notifyItemInserted(lista_menu.indexOf(menu));
    }
    @NonNull
    @Override
    public Holdermenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_menu,parent,false);
        Holdermenu holdermenu=new Holdermenu(view);
        return holdermenu;
    }

    @Override
    public void onBindViewHolder(@NonNull Holdermenu holder, int position) {
        holder.nombre_menu.setText(lista_menu.get(position).getNombre_menu());
        holder.precio.setText(lista_menu.get(position).getPrecio());
        holder.descripcion.setText(lista_menu.get(position).getDescripcion());
       /* Menuview it=lista_menu.get(position);
        Glide.with(context).load(it.getFotomenu()).into(holder.fotomenu);*/
    }

    @Override
    public int getItemCount() {
    return lista_menu.size();
    }

    class Holdermenu extends RecyclerView.ViewHolder{
        TextView nombre_menu,precio,descripcion;
        //ImageView fotomenu;
        public Holdermenu(@NonNull View itemView) {
            super(itemView);
            nombre_menu=itemView.findViewById(R.id.Menuview_titleRest);
            precio=itemView.findViewById(R.id.Menuview_precio);
            descripcion=itemView.findViewById(R.id.Menuview_descrip);
            //fotomenu=itemView.findViewById(R.id.imageViewMenu);
        }
    }

}
