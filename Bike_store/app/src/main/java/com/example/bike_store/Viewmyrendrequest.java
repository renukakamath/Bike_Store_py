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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewmyrendrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1;
    Button b1;
    ListView l1;
    String rent,login_id;
    String[] ren,date,value,statu,rent_request_id;
    public static String reqid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmyrendrequest);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.complaint);
        l1=(ListView)findViewById(R.id.list) ;
        l1.setOnItemClickListener(this);






        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewmyrendrequest.this;
        String q ="/viewrentrequest?login_id="+login.login_id+"&vid="+Addvehicle.vid;
        q = q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                ren=new String[ja1.length()];

                date=new String[ja1.length()];

                statu=new String[ja1.length()];

                value = new String[ja1.length()];
                rent_request_id = new String[ja1.length()];

                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    ren[i]=ja1.getJSONObject(i).getString("amount");

                    date[i]=ja1.getJSONObject(i).getString("date");

                    statu[i]=ja1.getJSONObject(i).getString("status");
                    rent_request_id[i]=ja1.getJSONObject(i).getString("rent_request_id");

                    value[i]="rent: "+ren[i]+"\ndate: "+date[i]+"\nstatus: "+statu[i];

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
        reqid=rent_request_id[i];
        final CharSequence[] items = {"make Payment","View Payment", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewmyrendrequest.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("make Payment")) {


                    startActivity(new Intent(getApplicationContext(), Rentmakepayment.class));



                }else if (items[item].equals("View Payment")) {


                    startActivity(new Intent(getApplicationContext(), Viewpayment.class));
                }


                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}