package com.example.comeya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class realizar_pedido extends AppCompatActivity {
    private RecyclerView recyclerViewproduct;
    private Pedido_adapter adaptadorProduct;
    private FloatingActionButton siguiente;
    private CardView ver_rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        recyclerViewproduct = (RecyclerView) findViewById(R.id.Pedido_productosList);
        recyclerViewproduct.setLayoutManager(new LinearLayoutManager(this));

        adaptadorProduct = new Pedido_adapter(obtenerproduc());
        recyclerViewproduct.setAdapter(adaptadorProduct);
        siguiente=(FloatingActionButton)findViewById(R.id.Pedido_buton_siguiente);
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
    }
    private void openVentana() {
        Ventana_vista_rest ventana_vista_rest=new Ventana_vista_rest();
        ventana_vista_rest.show(getSupportFragmentManager(),"example dialogo");
    }


    private List<PedidoView> obtenerproduc() {
        List<PedidoView> produc = new ArrayList<>();
        produc.add(new PedidoView("San isidro", "un rico manjar lleno de sabor y deliciosa salsa barbacu 100 porciento natural", "200 bs", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));

        return produc;
    }
}