package com.example.comeya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Confirmar_pedido extends AppCompatActivity {
    private RecyclerView recyclerViewproduct;
    private confirmarpedido_adapter adaptadorProduct;
    private Button pedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        pedido=(Button)findViewById(R.id.confirmarP_pedir);

        recyclerViewproduct = (RecyclerView)findViewById(R.id.listConfirmar);
        recyclerViewproduct.setLayoutManager(new LinearLayoutManager(this));

        adaptadorProduct = new confirmarpedido_adapter(obtenerproduc());
        recyclerViewproduct.setAdapter(adaptadorProduct);

    }
    private List<confirmarpedidoView> obtenerproduc() {
        List<confirmarpedidoView> confirmar = new ArrayList<>();
        confirmar.add(new confirmarpedidoView("aji de fideo 15 bs", "2", "2*15bs= 30bs"));
        confirmar.add(new confirmarpedidoView("aji de fideo 15 bs", "2", "2*15bs= 30bs"));
        confirmar.add(new confirmarpedidoView("aji de fideo 15 bs", "2", "2*15bs= 30bs"));
        confirmar.add(new confirmarpedidoView("aji de fideo 15 bs", "2", "2*15bs= 30bs"));
        return confirmar;
    }
}