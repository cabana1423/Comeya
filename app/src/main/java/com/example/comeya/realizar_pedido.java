package com.example.comeya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class realizar_pedido extends AppCompatActivity {
    private RecyclerView recyclerViewproduct;
    private Pedido_adapter adaptadorProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        recyclerViewproduct = (RecyclerView) findViewById(R.id.Pedido_productosList);
        recyclerViewproduct.setLayoutManager(new LinearLayoutManager(this));

        adaptadorProduct = new Pedido_adapter(obtenerproduc());
        recyclerViewproduct.setAdapter(adaptadorProduct);
    }

    private List<PedidoView> obtenerproduc() {
        List<PedidoView> produc = new ArrayList<>();
        produc.add(new PedidoView("San isidro", "un rico manjar lleno de sabor y deliciosa salsa barbacu 100 porciento natural", "200 bs", R.drawable.ic_fondo2));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));

        return produc;
    }
}