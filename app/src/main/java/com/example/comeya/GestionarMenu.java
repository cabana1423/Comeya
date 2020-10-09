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
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GestionarMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestionarMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GestionarMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment crearMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static GestionarMenu newInstance(String param1, String param2) {
        GestionarMenu fragment = new GestionarMenu();
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
    RecyclerView recyclerViewrest;
    LinearLayoutManager lnmyrest;
    GestionarMenu_adapter adaptadorproducto;
    private FloatingActionButton crear_Menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestionar_menu, container, false);
        recyclerViewrest =vista.findViewById(R.id.ListMymenus);
        crear_Menu =(FloatingActionButton)vista.findViewById(R.id.floatingButonNewMenu);
        crear_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        lnmyrest =new GridLayoutManager(context,1);
        adaptadorproducto =new GestionarMenu_adapter(context);
        recyclerViewrest.setLayoutManager(lnmyrest);
        recyclerViewrest.setAdapter(adaptadorproducto);
        obtener_menu();
        return  vista;
    }

    private void obtener_menu() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_VIEWMYMENU + RestData.ID_AUX_REST+"&id_us="+UserDataServer.ID+"&order=fecha_reg,-1",null,new JsonHttpResponseHandler(){
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
                        Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void openVentana() {
        Ventana ventanacrearmenu=new Ventana();
        ventanacrearmenu.show(getActivity().getSupportFragmentManager(),"example dialogo");
    }

   /* private List<PedidoView> obtenerproduc() {
        List<PedidoView> produc = new ArrayList<>();
        produc.add(new PedidoView("San isidro", "un rico manjar lleno de sabor y deliciosa salsa barbacu 100 porciento natural", "200 bs", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));

        return produc;
    }*/
}