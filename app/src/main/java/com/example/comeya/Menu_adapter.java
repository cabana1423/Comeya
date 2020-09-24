package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.comeya.utils.MenuData;

import java.util.ArrayList;
import java.util.Calendar;

public class Menu_adapter extends RecyclerView.Adapter<Menu_adapter.Holdermenu>{
    Context context;
    ArrayList<Menuview> lista_menu;
    //ArrayList<Menu_rest_view> lista_rest;
    Menu_adapter(Context context){
        this.context=context;
        lista_menu=new ArrayList<>();
        //lista_rest=new ArrayList<>();
    }
    void add(Menuview menu){
        lista_menu.add(menu);
        notifyItemInserted(lista_menu.indexOf(menu));
    }
    /*void add(Menu_rest_view rest){
        lista_rest.add(rest);
        notifyItemInserted(lista_rest.indexOf(rest));
    }*/
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
        holder.title_Rest.setText(lista_menu.get(position).getTitle_Rest());
        Menuview it=lista_menu.get(position);
        holder.id_rest=it.getId_rest();
        Glide.with(context).load(it.getImg_rest()).centerCrop().into(holder.fotorest);
        Glide.with(context).load(it.getFotomenu()).centerCrop().into(holder.fotomenu);;
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
    return lista_menu.size();
    }

    class Holdermenu extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre_menu,precio,descripcion,title_Rest;
        ImageView fotomenu,fotorest;
        CardView viewrest,viewmenu;
        String sDate,id_rest;
        public Holdermenu(@NonNull View itemView) {
            super(itemView);

            nombre_menu=itemView.findViewById(R.id.Menuview_titleMenu);
            precio=itemView.findViewById(R.id.Menuview_precio);
            descripcion=itemView.findViewById(R.id.Menuview_descrip);
            fotomenu=itemView.findViewById(R.id.imageViewMenu);
            viewrest=itemView.findViewById(R.id.principalview_rest);
            viewmenu=itemView.findViewById(R.id.principalview_menu);
            title_Rest=itemView.findViewById(R.id.Home_titleRest);
            fotorest=itemView.findViewById(R.id.Home_imgRest);
        }

        public void setOnclickCardview() {
            viewrest.setOnClickListener(this);
            viewmenu.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.principalview_rest:
                    MenuData.ID_AUX_rest=id_rest;
                    openVentana();
                    break;
                case R.id.principalview_menu:
                    MenuData.ID_AUX_rest=id_rest;
                    Calendar c = Calendar.getInstance();
                    sDate = c.get(Calendar.YEAR) + "-"
                            + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH)
                            + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)+ ":" + c.get(Calendar.SECOND);
                    String sDate2 = sDate.replaceAll("\\s","");
                    MenuData.TOKER_ORDER=sDate2;
                    Intent intent2=new Intent(context,realizar_pedido.class);
                    context.startActivity(intent2);
                    break;
            }
        }
        private void openVentana() {
            Ventana_vista_rest ventana_vista_rest=new Ventana_vista_rest();
            ventana_vista_rest.show(((AppCompatActivity)context).getSupportFragmentManager(),"example dialogo");
        }
    }

}
