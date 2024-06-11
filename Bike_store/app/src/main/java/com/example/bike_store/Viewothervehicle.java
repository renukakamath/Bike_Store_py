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

public class Viewothervehicle extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] veh,mod,br,col,value,vehicle_id,rent,rent_id;
    public static String rid,bid,amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewothervehicle);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewothervehicle.this;
        String q = "/Viewothervehicle?log_id=" +sh.getString("log_id", "");
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
                veh=new String[ja1.length()];

                mod=new String[ja1.length()];
                br=new String[ja1.length()];

                col=new String[ja1.length()];
                value=new String[ja1.length()];
                vehicle_id=new String[ja1.length()];
                rent=new String[ja1.length()];
                rent_id=new String[ja1.length()];

                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    veh[i]=ja1.getJSONObject(i).getString("vehicle");

                    mod[i]=ja1.getJSONObject(i).getString("model");

                    br[i]=ja1.getJSONObject(i).getString("brand");

                    col[i]=ja1.getJSONObject(i).getString("color");
                    vehicle_id[i]=ja1.getJSONObject(i).getString("vehicle_id");
                    rent[i]=ja1.getJSONObject(i).getString("amount");
                    rent_id[i]=ja1.getJSONObject(i).getString("rent_id");
                    value[i]="vehicle: "+veh[i]+"\nmodel: "+mod[i]+"\nbrand: "+br[i]+"\ncolor: "+col[i]+"\nrent: "+rent[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);


            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



        rid=rent_id[i];
        amt=rent[i];



        final CharSequence[] items = {"Send RentRequest", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewothervehicle.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Send RentRequest")) {



                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewothervehicle.this;
                    String q = "/RentRequest?log_id=" +sh.getString("log_id", "")+"&rid="+rid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(), "Successfully Requested", Toast.LENGTH_LONG).show();




                }


                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}