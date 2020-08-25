package com.example.comeya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
    }
    private RecyclerView recyclerViewproduct;
    private GestionarMenu_adapter adaptadorProduct;
    private FloatingActionButton crear_Menu;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestionar_menu, container, false);
        crear_Menu =(FloatingActionButton)vista.findViewById(R.id.floatingButonNewMenu);
        context=vista.getContext();
        crear_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        recyclerViewproduct = (RecyclerView) vista.findViewById(R.id.ListMymenus);
        recyclerViewproduct.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptadorProduct = new GestionarMenu_adapter(obtenerproduc());
        recyclerViewproduct.setAdapter(adaptadorProduct);
        return  vista;
    }
    private void openVentana() {
        Ventanacrearmenu ventanacrearmenu=new Ventanacrearmenu();
        ventanacrearmenu.show(getActivity().getSupportFragmentManager(),"example dialogo");
    }

    private List<PedidoView> obtenerproduc() {
        List<PedidoView> produc = new ArrayList<>();
        produc.add(new PedidoView("San isidro", "un rico manjar lleno de sabor y deliciosa salsa barbacu 100 porciento natural", "200 bs", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));

        return produc;
    }
}