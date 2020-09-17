package com.example.comeya;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
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
        obtenermenu();
        return root;
    }
    //private String nombre_menu,precio,descripcion,foto;
    private void obtenermenu() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERVICE_LISTMENU,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        adaptadormenu.add(new Menuview(obj.getString("nombre_menu"),obj.getString("precio"),
                                obj.getString("descripcion"),obj.getString("foto_produc")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}





/*
if (obj.has("nombre_menu")){
                            nombre_menu=obj.getString("nombre_menu");
                        }
                        else
                            nombre_menu="";
                        if (obj.has("precio")){
                            precio=obj.getString("precio");
                        }
                        else
                            precio="";
                        if (obj.has("descripcion")){
                            descripcion=obj.getString("descripcion");
                        }
                        else
                            descripcion="";
                        if (obj.has("foto_produc")){
                            foto=obj.getString("foto_produc");
                        }
                        else
                            foto="";
                        adaptadormenu.add(new Menuview(nombre_menu,precio, descripcion,foto));*/

