package com.example.comeya.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comeya.CreatePdf.TemplatePDF;
import com.example.comeya.MainActivity;
import com.example.comeya.R;
import com.example.comeya.SendEmail.JavaMailAPI;
import com.example.comeya.confirmarpedidoView;
import com.example.comeya.entrada;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.FacData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class Gestionar_Serv extends AppCompatActivity {
    TextView total;
    Button guardar;
    RadioGroup estados;
    RadioButton c1,c2,c3;
    private String id_cliente="";
    //mostrar lista
    RecyclerView recyclerViewfac;
    LinearLayoutManager lnmyfac;
    Gestionar_serv_Adapter adaptadormyfacFinal;
    //crear pdfs.....................private String[]header={"id","Nombre","Apellido"};
    private String longText="";
    private String TotalPaga="";
    private TemplatePDF templatePDF;
    //enviar email
    //EmailSender emailSender;
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

        estados=findViewById(R.id.Gestionar_serv_radioGroup);
        c1=findViewById(R.id.Gestiona_serv_espera);
        c2=findViewById(R.id.Gestiona_serv_proceso);
        c3=findViewById(R.id.Gestiona_serv_enviado);
        guardar=findViewById(R.id.Gestiona_serv_buton);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });
        //emailSender=new EmailSender();
        MostrarFac();
        changestadoradioB();
    }

    private void validar() {
        if(c1.isChecked()){
            Toast.makeText(getApplicationContext(),"el producto esta en espera",Toast.LENGTH_LONG).show();
        }
        if(c2.isChecked()){
            crearFacPdf();
            templatePDF.viewAppPDF(this);
        }
        if(c3.isChecked()){
            notificacion();
            enviarCorreo();
            startActivity(new Intent(Gestionar_Serv.this, Ticket_Localizacion.class ));
        }
    }

    private void changestadoradioB() {
        File archivo = new File(FacData.PathPdfs+"/"+FacData.ID_FAC+"-Ticket.pdf");
        log.d("esta es la direciooooon archivo",""+archivo);
        if (archivo.exists()) {
            c1.setChecked(false);
            c2.setChecked(true);
            c3.setChecked(false);
        }
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
                        total.setText("Total a cancelar: "+obj.getString("total_cancelo"));
                        TotalPaga=obj.getString("total_cancelo");
                        id_cliente=obj.getString("idUser_fac");
                        FacData.latitud=obj.getString("lati");
                        FacData.longitud=obj.getString("longi");
                        log.d("AQUI ESTAL LAS COORDENADAS PARA TICKET",FacData.latitud+""+FacData.longitud);

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
                        adaptadormyfacFinal.add(new confirmarpedidoView(obj.getString("nombre_menu"), "Unidades: "+obj.getString("cantidad"),
                                "total producto: "+obj.getString("pago_total"), obj.getString("_id")));
                        longText=longText+obj.getString("nombre_menu")+"           "
                                +obj.getString("cantidad")+"            "
                                +obj.getString("pago_total")+"\n";

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void notificacion() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERVICE_GETtoken_USER+ id_cliente,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject obj =response.getJSONObject(0);
                    notificarcliente(obj.getString("tokenFB"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("obtener token",""+errorResponse);
            }
        });
    }

    private void notificarcliente(String token) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        RequestParams params=new RequestParams();
        params.add("token", token);
        params.add("title", "Su pedido fue atendido");
        params.add("body", "Felicidades su pedido fue atendido con exito se le mando la factura corespondiente a su correo");
        client.post(EndPoints.SERV_POST_TOKENFB,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("enviar notificacion",""+errorResponse);
            }
        });
    }

    private void enviarCorreo() {
        String mail ="cabanaxsiempre@gmail.com";
        String message ="vamos a darle";
        String subject ="ho no wuaaaaaa >:|";

        JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,message,subject);
        javaMailAPI.execute();

    }

    private void crearFacPdf() {
        templatePDF = new TemplatePDF(getApplicationContext());
        templatePDF.OpenDocument();
        templatePDF.addMetaData("Factura", "Ventas", "ComeYa!");
        //FECHAAAAAAAA
        Calendar c = Calendar.getInstance();
        String sDate = c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);

        templatePDF.addTitles("Tienda el paraiso", "Pedidos", sDate);
        templatePDF.addParagraph(longText);
        templatePDF.addParagraph(TotalPaga);
        templatePDF.closeDocument();
    }
}












/*private ArrayList<String[]>getClients(){
        ArrayList<String[]>rows=new ArrayList<>();
        rows.add(new String[]{"1","pedro","Cabana"});
        rows.add(new String[]{"2","juan","Cabana"});
        rows.add(new String[]{"3","marco","Cabana"});
        rows.add(new String[]{"4","lucho","Cabana"});
        return rows;
    }*/
