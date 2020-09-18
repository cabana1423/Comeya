package com.example.comeya;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class GestionarMenu_adapter extends RecyclerView.Adapter<GestionarMenu_adapter.Holdermenu>{
    Context context;
    ArrayList<PedidoView> lista_menu;
    GestionarMenu_adapter(Context context){
        this.context=context;
        lista_menu=new ArrayList<>();
    }
    void add(PedidoView menu){
        lista_menu.add(menu);
        notifyItemInserted(lista_menu.indexOf(menu));
    }
    @NonNull
    @Override
    public Holdermenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_pedido,parent,false);
        Holdermenu holdermenu=new Holdermenu(view);
        return holdermenu;
    }

    @Override
    public void onBindViewHolder(@NonNull Holdermenu holder, int position) {
        holder.titleprduc.setText(lista_menu.get(position).getTitleproducto());
        holder.precio.setText(lista_menu.get(position).getPrecio());
        holder.descripcion.setText(lista_menu.get(position).getDescripcion());
        PedidoView it=lista_menu.get(position);
        holder.id_menu=it.getId_menu();
        Glide.with(context).load(it.getFotoproducto()).centerCrop().into(holder.fotopruct);
        holder.setOnclickCardview();

    }

    @Override
    public int getItemCount() {
        return lista_menu.size();
    }

    class Holdermenu extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private TextView titleprduc, precio, descripcion;
        private ImageView fotopruct;
        private CardView pedido;
        private String id_menu;
        public Holdermenu(@NonNull View itemView) {
            super(itemView);
            titleprduc =(TextView)itemView.findViewById(R.id.Produc_titulo);
            precio =(TextView)itemView.findViewById(R.id.produc_precio);
            descripcion =(TextView)itemView.findViewById(R.id.produc_numimg);
            fotopruct =(ImageView) itemView.findViewById(R.id.produc_img);
            pedido =(CardView) itemView.findViewById(R.id.groupviewpedidoproducto);

        }
        public void setOnclickCardview() {
            pedido.setOnClickListener(this);
        }
        public void onClick(View v){
            showPopMenu(v);
        }

        private void showPopMenu(View view){
            PopupMenu popupMenu=new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.option_gestionarmenu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_menu:
                    elimar();
                    return true;
            }
            return false;
        }
        private void elimar() {
            AsyncHttpClient client=new AsyncHttpClient();
            client.addHeader("Authorization", UserDataServer.TOKEN);
            client.delete(EndPoints.SERV_DELETEMENU+ id_menu,null,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                }
            });
        }

    }
}
