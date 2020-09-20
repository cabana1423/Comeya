package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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
        Menuview it=lista_menu.get(position);
        Glide.with(context).load(it.getFotomenu()).centerCrop().into(holder.fotomenu);
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
    return lista_menu.size();
    }

    class Holdermenu extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre_menu,precio,descripcion;
        ImageView fotomenu;
        CardView viewrest,viewmenu;
        String sDate;
        public Holdermenu(@NonNull View itemView) {
            super(itemView);
            nombre_menu=itemView.findViewById(R.id.Menuview_titleRest);
            precio=itemView.findViewById(R.id.Menuview_precio);
            descripcion=itemView.findViewById(R.id.Menuview_descrip);
            fotomenu=itemView.findViewById(R.id.imageViewMenu);
            viewrest=itemView.findViewById(R.id.principalview_rest);
            viewmenu=itemView.findViewById(R.id.principalview_menu);
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
                    openVentana();
                    break;
                case R.id.principalview_menu:
                    Calendar c = Calendar.getInstance();
                    sDate = c.get(Calendar.YEAR) + "-"
                            + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH)
                            + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)+ ":" + c.get(Calendar.SECOND);
                    MenuData.TOKER_ORDER=sDate;
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
