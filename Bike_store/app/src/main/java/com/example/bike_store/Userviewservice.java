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

public class Userviewservice extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] service,description,rate,service_type_id,service_id,service_type,value;
    public static String sid,bid,amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewservice);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewservice.this;
        String q = "/Userviewservice?log_id=" +sh.getString("log_id", "");
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
                service = new String[ja1.length()];

                description = new String[ja1.length()];
                rate = new String[ja1.length()];
                service_type_id = new String[ja1.length()];
                service_id = new String[ja1.length()];
                service_type = new String[ja1.length()];

                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    service[i] = ja1.getJSONObject(i).getString("service");

                    rate[i] = ja1.getJSONObject(i).getString("rate");
                    description[i] = ja1.getJSONObject(i).getString("description");
                    service_type_id[i] = ja1.getJSONObject(i).getString("service_type_id");

                    service_id[i] = ja1.getJSONObject(i).getString("service_id");
                    service_type[i] = ja1.getJSONObject(i).getString("service_type");




                    value[i] = "service:" + service[i] + "\nrate: " + rate[i] + "\n description: " + description[i]   + "\n service_type: " + service_type[i]   ;

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
        sid=service_id[i];
        final CharSequence[] items = {"View TimeSlot", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewservice.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View TimeSlot")) {


                    startActivity(new Intent(getApplicationContext(), Viewtimeslot.class));


                } else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}