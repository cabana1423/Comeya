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
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
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
 * Use the {@link add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu.
     */
    // TODO: Rename and change types and number of parameters
    public static add newInstance(String param1, String param2) {
        add fragment = new add();
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
    MyRest_Adapter adaptadormyrest;
    private FloatingActionButton crear_rest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        recyclerViewrest =root.findViewById(R.id.Myrestview_list);
        crear_rest=(FloatingActionButton)root.findViewById(R.id.addrest_floatingButton);
        crear_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,crear_Myrest.class);
                context.startActivity(intent);
            }
        });
        lnmyrest =new GridLayoutManager(context,1);
        adaptadormyrest =new MyRest_Adapter(context);
        recyclerViewrest.setLayoutManager(lnmyrest);
        recyclerViewrest.setAdapter(adaptadormyrest);
        obtener_rest();
        return root;
    }

    private void obtener_rest() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERVICE_LISTREST,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        adaptadormyrest.add(new restView(obj.getString("nombre_rest"),obj.getString("calle")
                                ,obj.getString("telefono"),obj.getString("foto_lugar"),obj.getString("lat"),obj.getString("lon"),obj.getString("_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /*public List<restView> obtenerMyRest(){
        List<restView> rest=new ArrayList<>();
        rest.add(new restView("San isidro","boqueron","72737475",R.drawable.image_defect));
        rest.add(new restView("San isidro","boqueron","72737475",R.drawable.image_defect));
        rest.add(new restView("San isidro","boqueron","72737475",R.drawable.image_defect));

        return rest;
    }*/
}