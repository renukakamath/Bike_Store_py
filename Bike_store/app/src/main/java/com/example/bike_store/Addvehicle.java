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

public class Addvehicle extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1,e2,e3,e4;
    Button b1;
    ListView l1;
    String vehicle,model,brand,color;
    String[] veh,mod,br,col,value,vehicle_id;
    SharedPreferences sh;
    public static  String vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvehicle);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.vehicle);
        e2=(EditText) findViewById(R.id.model);
        e3=(EditText) findViewById(R.id.brand);
        e4=(EditText) findViewById(R.id.color);



        l1=(ListView)findViewById(R.id.list) ;

        l1.setOnItemClickListener(this);
        b1=(Button)findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicle=e1.getText().toString();
                model=e2.getText().toString();
                brand=e3.getText().toString();
                color=e4.getText().toString();


                if(vehicle.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your vehicle");
                    e1.setFocusable(true);
                }
                else if(model.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your model");
                    e2.setFocusable(true);
                }
                else if(brand.equalsIgnoreCase(""))
                {
                    e3.setError("Enter your brand");
                    e3.setFocusable(true);
                }
                else if(color.equalsIgnoreCase(""))
                {
                    e4.setError("Enter your color");
                    e4.setFocusable(true);
                }else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Addvehicle.this;
                    String q = "/Addvehicle?&login_id=" + login.login_id + "&vehicle=" + vehicle + "&model=" + model + "&brand=" + brand + "&color=" + color;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }
            }
        });



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addvehicle.this;
        String q ="/Viewvehicle?login_id="+login.login_id;
        q = q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Addvehicle")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Addrent.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("Viewvehicle"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    veh=new String[ja1.length()];

                    mod=new String[ja1.length()];
                    br=new String[ja1.length()];

                    col=new String[ja1.length()];
                    value=new String[ja1.length()];
                    vehicle_id=new String[ja1.length()];
                    for(int i = 0;i<ja1.length();i++)
                    {
                        veh[i]=ja1.getJSONObject(i).getString("vehicle");

                        mod[i]=ja1.getJSONObject(i).getString("model");

                        br[i]=ja1.getJSONObject(i).getString("brand");

                        col[i]=ja1.getJSONObject(i).getString("color");
                        vehicle_id[i]=ja1.getJSONObject(i).getString("vehicle_id");
                        value[i]="vehicle: "+veh[i]+"\nmodel: "+mod[i]+"\nbrand: "+br[i]+"\ncolor: "+col[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.custtext,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        vid=vehicle_id[i];
        final CharSequence[] items = {"Add Rent","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Addvehicle.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add Rent")) {


                    startActivity(new Intent(getApplicationContext(), Addrent.class));



                }



                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
    }
