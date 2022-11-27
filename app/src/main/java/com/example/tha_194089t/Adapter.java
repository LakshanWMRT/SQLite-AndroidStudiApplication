package com.example.tha_194089t;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList item_id, item_name, item_description, item_price;


    Adapter(Activity activity, Context context, ArrayList itme_id, ArrayList item_name, ArrayList item_description,
            ArrayList item_price) {

        this.item_id = itme_id;
        this.item_name = item_name;
        this.item_description = item_description;
        this.item_price = item_price;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_insert_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.item_id_txt.setText(String.valueOf(item_id.get(position)));
        holder.item_name_txt.setText(String.valueOf(item_name.get(position)));
        holder.item_description_txt.setText(String.valueOf(item_description.get(position)));
        holder.item_price_txt.setText(String.valueOf(item_price.get(position)));
        //assign 3 pictures according to id no
        int pic_no = position % 3;
        holder.cake_image.setImageResource(pic_no == 0 ? R.drawable.caramel_cake : pic_no == 1 ? R.drawable.choco_cake :  R.drawable.orange_cake);


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InsertItem.class);
                intent.putExtra("id", String.valueOf(item_id.get(position)));
                intent.putExtra("name", String.valueOf(item_name.get(position)));
                intent.putExtra("description", String.valueOf(item_description.get(position)));
                intent.putExtra("price", String.valueOf(item_price.get(position)));
                activity.startActivityForResult(intent, 2);
            }
        });


    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_id_txt, item_name_txt,  item_price_txt, item_description_txt;
        Button mainLayout;
        ImageView cake_image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id_txt = itemView.findViewById(R.id.item_id_txt);
            item_name_txt = itemView.findViewById(R.id.item_name_txt);
            item_description_txt = itemView.findViewById(R.id.item_description_txt);
            item_price_txt = itemView.findViewById(R.id.item_price_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            cake_image = itemView.findViewById(R.id.cakeImage);

        }

    }
}
