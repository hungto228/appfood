package com.example.phantom_project.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phantom_project.InterFace.ItemOnclickListener;
import com.example.phantom_project.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuViewAdapter extends RecyclerView.ViewHolder {
    public TextView mMenuname;
   public ImageView imgView;
    private ItemOnclickListener itemOnclickListener;

    public MenuViewAdapter(@NonNull View itemView) {
        super(itemView);
        mMenuname = itemView.findViewById(R.id.tv_menuName);
        imgView = itemView.findViewById(R.id.img_menu);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnclickListener.onClick(v, getAdapterPosition(), false);
            }
        });

    }

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }
}
