package com.example.bike_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Addcomments extends AppCompatActivity implements JsonResponse {
    EditText e1;
    Button b1;
    ListView l1;
    String complaint,login_id;
    String[] complaints,date,value;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomments);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.complaint);
        l1=(ListView)findViewById(R.id.list) ;
        b1=(Button)findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint=e1.getText().toString();

                if(complaint.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your complaint");
                    e1.setFocusable(true);
                }
                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Addcomments.this;
                    String q = "/Addcomments?&login_id=" + login.login_id + "&complaint=" + complaint + "&bid=" + Userviewblog.bid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addcomments.this;
        String q ="/viewcomments?login_id="+login.login_id+"&bid="+Userviewblog.bid;
        q = q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Addcomments")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Addcomments.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("viewcomments"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    complaints=new String[ja1.length()];

                    date=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        complaints[i]=ja1.getJSONObject(i).getString("comments");

                        date[i]=ja1.getJSONObject(i).getString("date");
                        value[i]="complaint: "+complaints[i]+"\ndate: "+date[i];

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
}
