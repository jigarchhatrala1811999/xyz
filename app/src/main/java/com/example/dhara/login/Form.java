package com.example.dhara.login;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
public class Form extends AppCompatActivity {
    EditText username, firstname, lastname, phone, password, confirmpassword, email,url;
    SharedPreferences sharedPreferences;
    String urlss="";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        url=findViewById(R.id.urlssss);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        requestQueue= Volley.newRequestQueue(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1);
        username = findViewById(R.id.username_edt);
        firstname = findViewById(R.id.fname_edt);
        lastname = findViewById(R.id.lname_edt);
        phone = findViewById(R.id.phone_edt);
        password = findViewById(R.id.password_edt);
        confirmpassword = findViewById(R.id.confirm_password);
        email = findViewById(R.id.phone_edt);
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        urlss=sharedPreferences.getString("url","");
        if(!urlss.isEmpty()){
            this.url.setVisibility(View.GONE);
        }
    }

    public void submit(View view) {
        final String uname,lname,p,pass,conpass,em,fname,url;
        if(urlss.isEmpty()){
            urlss=this.url.getText().toString();
            if(urlss.isEmpty()){
                Toast.makeText(this, "Please Enter Url", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        sharedPreferences.edit().putString("url",urlss).apply();
        uname=username.getText().toString();
        lname=lastname.getText().toString();
        p=phone.getText().toString();
        pass=password.getText().toString();
        conpass=confirmpassword.getText().toString();
        em=email.getText().toString();
        fname=firstname.getText().toString();
        if(check(uname,lname,p,pass,conpass,em,fname)){
            if(pass.equals(conpass)){
                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlss, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Form.this, "Sucessfully Upload", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Form.this, "No Internet", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Form.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map=new HashMap<>();
                        map.put("fname",fname);
                        map.put("lname",lname);
                        map.put("username",uname);
                        map.put("password",pass);
                        map.put("phone",p);
                        map.put("email",em);
                        return map;
                    }
                };
                progressDialog.show();
                requestQueue.add(stringRequest);
            }else{
                Toast.makeText(this, "Password are Not same", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean check(String...a){
        for(String e:a){
            if(e.isEmpty()){
                Toast.makeText(this, "Please Fill Form ", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
