package com.voanhhao.kiemtra_hk2.cau2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.voanhhao.kiemtra_hk2.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<Product> products;

    public ProductAdapter(MainActivity context, int item_layout, List<Product> products) {
        this.context = context;
        this.item_layout = item_layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            holder.txtInfo = convertView.findViewById(R.id.txtInfo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product p = products.get(position);
        holder.txtInfo.setText("Mã sản phẩm: " + p.getProductCode() + "\n" + "Tên sản phẩm: " + p.getProductName() + "\n" + "Giá bán: " +  String.format("%.0fđ", p.getProductPrice()));
        holder.txtInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.openEditDialog(p);
                return true;
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        TextView txtInfo;
    }
}
