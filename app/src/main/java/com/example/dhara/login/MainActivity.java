package com.example.dhara.login;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String username="swachil123";
    public static final String pass="12345";
    EditText editText,editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.username);
        editText1=findViewById(R.id.password);
    }

    public void submitlogin(View view){
        String username,password;
        username=editText.getText().toString();
        password=editText1.getText().toString();
        if(check(username,password)){
            if(username.equals(MainActivity.username)&&password.equals(pass)){
                Intent intent=new Intent(this,Form.class);
                startActivity(intent);
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
