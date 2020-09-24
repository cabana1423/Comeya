package com.example.comeya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class realizar_pedido extends AppCompatActivity {
    RecyclerView recyclerViewpedido;
    LinearLayoutManager lnmypedido;
    Pedido_adapter adaptadorproducto;
    TextView phone_rest;
    ImageView img_rest;
    private realizar_pedido root=this;
    private FloatingActionButton siguiente, cancel;
    private CardView ver_rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        recyclerViewpedido =findViewById(R.id.Pedido_productosList);
        lnmypedido =new GridLayoutManager(this,1);
        adaptadorproducto =new Pedido_adapter(this);
        recyclerViewpedido.setLayoutManager(lnmypedido);
        recyclerViewpedido.setAdapter(adaptadorproducto);
        phone_rest=findViewById(R.id.Pedido_phone_rest);
        img_rest=findViewById(R.id.Pedido_imagerest);

        siguiente=(FloatingActionButton)findViewById(R.id.Pedido_buton_siguiente);
        cancel=(FloatingActionButton)findViewById(R.id.pedido_buton_cancelar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"cancelaste vato :(",Toast.LENGTH_SHORT).show();
            }
        });
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(realizar_pedido.this, Confirmar_pedido.class ));
            }
        });
        ver_rest=(CardView)findViewById(R.id.contenedor_rest_pedido);
        ver_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        vista_rest();
        obtener_pedido();
    }

    private void vista_rest() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST + MenuData.ID_AUX_rest, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject obj = response.getJSONObject(0);
                    phone_rest.setText(obj.getString("telefono"));
                    Glide.with(root).load(obj.getString("foto_lugar")).centerCrop().into(img_rest);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obtener_pedido() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_VIEWMENU,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        adaptadorproducto.add(new PedidoView(obj.getString("nombre_menu"),obj.getString("descripcion")
                                ,obj.getString("precio"),obj.getString("foto_produc"),obj.getString("_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void openVentana() {
        Ventana_vista_rest ventana_vista_rest=new Ventana_vista_rest();
        ventana_vista_rest.show(getSupportFragmentManager(),"example dialogo");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("usted esta aunto de cancelar una operacion, desea salir?")
                    .setPositiveButton("si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AsyncHttpClient client=new AsyncHttpClient();
                            client.addHeader("Authorization", UserDataServer.TOKEN);
                            client.delete(EndPoints.SERV_DELETE_ORDER+ MenuData.TOKER_ORDER,null,new JsonHttpResponseHandler(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                    super.onSuccess(statusCode, headers, response);
                                }
                            });
                            onBackPressed();
                        }
                    })
                    .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    /*private List<PedidoView> obtenerproduc() {
        List<PedidoView> produc = new ArrayList<>();
        produc.add(new PedidoView("San isidro", "un rico manjar lleno de sabor y deliciosa salsa barbacu 100 porciento natural", "200 bs", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));

        return produc;
    }*/
}