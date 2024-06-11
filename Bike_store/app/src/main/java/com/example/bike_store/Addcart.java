package com.example.bike_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class Addcart extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4;
    Button b1;
    SharedPreferences sh;
    String qua,menu_id,login_id ,amt, tot;
    public static String mid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcart);

        e1=(EditText) findViewById(R.id.menu);
        e2=(EditText) findViewById(R.id.amount);
        e3=(EditText)findViewById(R.id.quantity) ;
        e4=(EditText) findViewById(R.id.total);

        b1=(Button) findViewById(R.id.book);
        e1.setText(Viewproduct.pname);
        e2.setText(Viewproduct.amt);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qua=e3.getText().toString();
                tot=e4.getText().toString();
                if(qua.equalsIgnoreCase(""))
                {
                    e3.setError("Enter your Quantity");
                    e3.setFocusable(true);
                }
                else if(tot.equalsIgnoreCase(""))
                {
                    e4.setError("Enter your Total");
                    e4.setFocusable(true);
                }else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Addcart.this;
                    String q = "/Addcart?quantity=" + qua + "&total=" + tot + "&pid=" + Viewproduct.pid + "&amt=" + Viewproduct.amt + "&spid=" + Viewproduct.spid + "&login_id=" + sh.getString("log_id", "");
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });

        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(e3.getText().toString().equalsIgnoreCase(""))
                {

                }
                else if(e3.getText().toString().equalsIgnoreCase("."))
                {

                }
                else
                {
                    Integer s=Integer.parseInt(e2.getText().toString())*Integer.parseInt(e3.getText().toString());
                    e4.setText(s+"");
                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}