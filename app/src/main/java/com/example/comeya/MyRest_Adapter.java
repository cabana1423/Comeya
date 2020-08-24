package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MyRest_Adapter extends RecyclerView.Adapter<MyRest_Adapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleRest, direccion, telefono;
        private ImageView fotorest;
        private CardView vistarest;
        Context context;

        public ViewHolder(View itemView){
            super(itemView);
            context=itemView.getContext();
            titleRest=(TextView)itemView.findViewById(R.id.Restviewtitulo);
            direccion=(TextView)itemView.findViewById(R.id.Resyviewdireccion);
            telefono=(TextView)itemView.findViewById(R.id.Restviewphone);
            fotorest=(ImageView) itemView.findViewById(R.id.Restviewimg);
            vistarest =(CardView) itemView.findViewById(R.id.Restgroupview);

        }
        void setOnclickCardview(){
            vistarest.setOnClickListener(this);
        };

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Restgroupview:
                    Intent intent=new Intent(context,administrar_rest.class);
                    context.startActivity(intent);
                    break;
            }
        }

    }
    public List<restView> MylistaRestview;

    public MyRest_Adapter(List<restView>listaRestview){
        this.MylistaRestview =listaRestview;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_rest,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleRest.setText(MylistaRestview.get(position).getTitleRest());
        holder.direccion.setText(MylistaRestview.get(position).getDireccion());
        holder.telefono.setText(MylistaRestview.get(position).getTelefono());
        holder.fotorest.setImageResource(MylistaRestview.get(position).getFotorest());
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
        return MylistaRestview.size();
    }
}
