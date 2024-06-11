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

public class Userviewbike extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] bike,model,brand,color,image,amount,value,shop_id,bike_id;
    public static String sid,bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewbike);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewbike.this;
        String q = "/Userviewbike?log_id=" +sh.getString("log_id", "")+"&sid="+UserViewshop.sid;
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
                bike = new String[ja1.length()];
                model = new String[ja1.length()];
                amount = new String[ja1.length()];
                brand = new String[ja1.length()];
                color = new String[ja1.length()];
                image = new String[ja1.length()];
                shop_id = new String[ja1.length()];
                bike_id = new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    bike[i] = ja1.getJSONObject(i).getString("bike");

                    model[i] = ja1.getJSONObject(i).getString("model");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    brand[i] = ja1.getJSONObject(i).getString("brand");
                    color[i] = ja1.getJSONObject(i).getString("color");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    shop_id[i] = ja1.getJSONObject(i).getString("shop_id");
                    bike_id[i] = ja1.getJSONObject(i).getString("bike_id");



                    value[i] = "bike:" + bike[i] + "\nmodel: " + model[i] + "\n amount: " + amount[i] + "\nbrand: " + brand[i] +"\ncolor:"+ color[i]   ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

                Custimage a = new Custimage(this, bike, model,brand,color,amount,image);
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
        bid=bike_id[i];
        sid=shop_id[i];

        final CharSequence[] items = {"Bike Request","Rate","View Bike Image","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewbike.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Bike Request")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Userviewbike.this;
                    String q = "/Bikerequest?log_id=" +sh.getString("log_id", "")+"&sid="+sid+"&bid="+bid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(), "Successfully Requested", Toast.LENGTH_LONG).show();





                } else if (items[item].equals("Rate")) {


                    startActivity(new Intent(getApplicationContext(), Rateing.class));
                }


                else if (items[item].equals("View Bike Image")) {


                    startActivity(new Intent(getApplicationContext(), Viewbikesimg.class));
                }



                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}