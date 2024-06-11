package com.example.bike_store;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewproduct extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] product_name,rate,stock,value,image,fname,product_id,spareparts_id,Brand;
    public static String pname,pid,amt,spid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproduct);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewproduct.this;
        String q = "/Viewproduct?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                product_name = new String[ja1.length()];
                rate = new String[ja1.length()];
                stock = new String[ja1.length()];
                image = new String[ja1.length()];
                fname = new String[ja1.length()];
                product_id = new String[ja1.length()];
                spareparts_id = new String[ja1.length()];
                Brand= new String[ja1.length()];
                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    product_name[i] = ja1.getJSONObject(i).getString("product_name");

                    rate[i] = ja1.getJSONObject(i).getString("rate");
                    stock[i] = ja1.getJSONObject(i).getString("stock");
                    image[i] = ja1.getJSONObject(i).getString("image");

                    fname[i] = ja1.getJSONObject(i).getString("fname");
                    product_id[i] = ja1.getJSONObject(i).getString("product_id");

                    spareparts_id[i] = ja1.getJSONObject(i).getString("spareparts_id");
                    Brand[i] = ja1.getJSONObject(i).getString("Brand");

                    value[i] = "product_name:" + product_name[i] + "\nrate: " + rate[i]+ "\nBrand: " + Brand[i] + "\n stock: " + stock[i] + "\n fname: " + fname[i]    ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

                Custimage1 a = new Custimage1(this, product_name, rate,stock,image,fname);
                l1.setAdapter(a);



            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pid=product_id[i];
        spid=spareparts_id[i];
        pname=product_name[i];
        amt=rate[i];


        final CharSequence[] items = {"Add To Cart","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewproduct.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add To Cart")) {


                    startActivity(new Intent(getApplicationContext(), Addcart.class));



                }



                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}