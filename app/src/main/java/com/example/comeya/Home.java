package com.example.comeya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.comeya.notificaciones.Notificaciones;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context=getActivity();

    }
    Context context;
    RecyclerView recyclerViewmenu;
    LinearLayoutManager lnmenu;
    Menu_adapter adaptadormenu;
    ImageButton noti;
    //String Id_Rest,nombre_menu, precio,descripcion,fotomenu,title_rest,img_rest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewmenu=root.findViewById(R.id.listaViewMenu);
        lnmenu=new GridLayoutManager(context,1);
        adaptadormenu=new Menu_adapter(context);
        recyclerViewmenu.setLayoutManager(lnmenu);
        recyclerViewmenu.setAdapter(adaptadormenu);
        noti=root.findViewById(R.id.notificaciones);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Notificaciones.class ));
            }
        });
        obtenermenu();
        return root;
    }
    private void obtenermenu() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERVICE_LISTMENU+"?order=fecha_reg,-1",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        adaptadormenu.add(new Menuview(obj.getString("nombre_menu"),"precio: "+obj.getString("precio")+" Bs",obj.getString("descripcion")
                                ,obj.getString("foto_produc"),obj.getString("id_rest_menu"),obj.getString("nombre_rest"),obj.getString("url_rest")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}