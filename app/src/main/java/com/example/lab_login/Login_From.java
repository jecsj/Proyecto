package com.example.lab_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Login_From extends AppCompatActivity {

    private LoginDatabaseHelper myDb;
    private TextInputLayout editEmail,editPassword;
    private Button btnfindDataByEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login__from);
        getSupportActionBar().setTitle("Login Form");
        myDb=new LoginDatabaseHelper(this);
        editEmail=(TextInputLayout)findViewById(R.id.editText_email2);
        editPassword=(TextInputLayout)findViewById(R.id.editText_password2);
        btnfindDataByEmail=(Button)findViewById(R.id.button_login);
        findDataByEmail();

    }
    public void btn_login(View view)
    {

    }

    public void findDataByEmail()
    {
        btnfindDataByEmail.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Cursor res=myDb.findDataByEmail(editEmail.getEditText().getText().toString(),editPassword.getEditText().getText().toString());
                        if(res.getCount()==0)
                        {
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                            if(res.getString(5).equals("1"))
                            {
                                startActivity(new Intent(getApplicationContext(),MenoUsuario.class));

                            }
                            if(res.getString(5).equals("2"))
                            {
                                startActivity(new Intent(getApplicationContext(),MenoEntidad.class));

                            }
                        }

                        showMessage("Data",buffer.toString());
                    }
                }

        );
    }
    public void showMessage(String tittle, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.show();
    }
    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), SingUp_Form.class));

    }
}