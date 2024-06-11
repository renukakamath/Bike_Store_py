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

public class Viewcart extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] product_name,rate,value,image,fname,product_id,spareparts_id,quantity,date,statu,omaster_id;
    public static String omid,pid,amt,stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcart);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewcart.this;
        String q = "/Viewcart?log_id=" +sh.getString("log_id", "");
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
                quantity = new String[ja1.length()];
                image = new String[ja1.length()];
                fname = new String[ja1.length()];
                product_id = new String[ja1.length()];
                spareparts_id = new String[ja1.length()];
                date=new String[ja1.length()];
                statu=new String[ja1.length()];

                value = new String[ja1.length()];
                omaster_id= new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    product_name[i] = ja1.getJSONObject(i).getString("product_name");

                    rate[i] = ja1.getJSONObject(i).getString("amount");
                    quantity[i] = ja1.getJSONObject(i).getString("quantity");
                    image[i] = ja1.getJSONObject(i).getString("image");

                    fname[i] = ja1.getJSONObject(i).getString("fname");
                    product_id[i] = ja1.getJSONObject(i).getString("product_id");

                    spareparts_id[i] = ja1.getJSONObject(i).getString("spareparts_id");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    omaster_id[i] = ja1.getJSONObject(i).getString("omaster_id");



                    value[i] = "product_name:" + product_name[i] + "\nrate: " + rate[i] + "\n quantity: " + quantity[i] + "\n fname: " + fname[i]+ "\n date: " + date[i] + "\n status: " + statu[i]    ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

                Custimage2 a = new Custimage2(this, product_name, rate,quantity,image,fname,date,statu);
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
        amt = rate[i];
        omid = omaster_id[i];
        stat=statu[i];


        if (stat.equalsIgnoreCase("Accept")) {
            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewcart.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Make Payment")) {


                        startActivity(new Intent(getApplicationContext(), Cartpayment.class));


                    }else if (items[item].equals("Rate")) {

                        startActivity(new Intent(getApplicationContext(), Rate.class));

                    }

                    else if (items[item].equals("Cancel")) {


                        dialog.dismiss();
                    }
                }

            });
            builder.show();
        }else if (stat.equalsIgnoreCase("Paid")) {
            final CharSequence[] items = {"Rate", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewcart.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Rate")) {

                        startActivity(new Intent(getApplicationContext(), Rate.class));

                    }

                    else if (items[item].equals("Cancel")) {


                        dialog.dismiss();
                    }
                }

            });
            builder.show();
        }

    }
}