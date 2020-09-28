package com.example.comeya.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comeya.R;
import com.example.comeya.confirmarpedidoView;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.FacData;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class Gestionar_Serv extends AppCompatActivity {
    TextView total;
    Button guardar;
    RadioGroup estados;
    //String id_client="", toker="";

    RecyclerView recyclerViewfac;
    LinearLayoutManager lnmyfac;
    Gestionar_serv_Adapter adaptadormyfacFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar__serv);
        total=findViewById(R.id.Gestiona_serv_total);
        recyclerViewfac =findViewById(R.id.Gestiona_serv_lista);
        lnmyfac =new GridLayoutManager(this,1);
        adaptadormyfacFinal =new Gestionar_serv_Adapter(this);
        recyclerViewfac.setLayoutManager(lnmyfac);
        recyclerViewfac.setAdapter(adaptadormyfacFinal);
        MostrarFac();
    }

    private void MostrarFac() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GET_ONEFAC+ FacData.ID_FAC,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(0);
                        log.d("para el id","    el id      "+obj.getString("idUser_fac")+"     el toker    "+obj.getString("toker"));
                        MostrarLista(obj.getString("toker"),obj.getString("idUser_fac"));
                        total.setText(obj.getString("total_cancelo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void MostrarLista(String toker, String id_client) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GET_ORDER + toker+"&user="+id_client, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        adaptadormyfacFinal.add(new confirmarpedidoView(obj.getString("nombre_menu"), obj.getString("cantidad"),
                                obj.getString("pago_total"), obj.getString("_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
/*
 */