package com.voanhhao.kiemtra_hk2.cau2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.voanhhao.kiemtra_hk2.R;
import com.voanhhao.kiemtra_hk2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prepareDb();
        loadData();

    }

    private void loadData() {
        adapter = new ProductAdapter(MainActivity.this, R.layout.item_list, getDataFromDb());
        binding.lsvProduct.setAdapter(adapter);
    }

    private List<Product> getDataFromDb() {
        products = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + Database.TBL_NAME);
        while (cursor.moveToNext()){
            products.add(new Product(cursor.getString(0),  cursor.getString(1), cursor.getDouble(2)));
        }
        cursor.close();
        return products;
    }

    public void openEditDialog(Product p){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add);

        EditText edtCode = dialog.findViewById(R.id.edtID);
        EditText edtName = dialog.findViewById(R.id.edtName);
        EditText edtPrice = dialog.findViewById(R.id.edtPrice);

        //hiển thị thông tin sản phẩm
        edtCode.setText(String.valueOf(p.getProductCode()));
        edtName.setText(p.getProductName());
        edtPrice.setText(String.valueOf(p.getProductPrice()));

        //Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Insert
//                String id = edtId.getText().toString();
//                String name = edtName.getText().toString();
//                Double price = Double.valueOf(edtPrice.getText().toString());
//                db.execSql("INSERT INTO " + Database.TBL_NAME + " VALUES(null, '" + id + "', '" + name + "', " + price + ")");
//                loadData();
//                dialog.dismiss();
//            }
//        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Do you want delete "+p.getProductName()+"?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        db.execSql("DELETE FROM "+ Database.TBL_NAME+ " WHERE "+ Database.COL_CODE+" = "+ p.getProductCode());
                        loadData();
                        dialogInterface.dismiss();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void prepareDb() {
        db = new Database(MainActivity.this);
        db.createSampleData();
    }
}