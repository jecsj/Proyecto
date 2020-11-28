package com.example.lab_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SingUp_Form extends AppCompatActivity {

    private LoginDatabaseHelper myDb;
    private TextInputLayout editId,editFullName,editUsername,editEmail,editPassword;
    private RadioButton radioMale, radioFemale;
    private RadioGroup radioGroup;
    private Button btnAddData,btnViewAll;
    private View radioButton;
    private int tipousuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up__form);
        getSupportActionBar().setTitle("SingUp_Form");
        myDb=new LoginDatabaseHelper(this);
        editId=(TextInputLayout)findViewById(R.id.editText_id);
        editFullName=(TextInputLayout)findViewById(R.id.editText_fullName);
        editUsername=(TextInputLayout)findViewById(R.id.editText_userName);
        editEmail=(TextInputLayout)findViewById(R.id.editText_email);
        editPassword=(TextInputLayout)findViewById(R.id.editText_password);
        btnAddData=(Button)findViewById(R.id.button_register);
        btnViewAll=(Button)findViewById(R.id.button_viewall);

        addData();
        viewAll();
    }
    public void addData()
    {
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Integer valueInt =new Integer(tipousuario);
                        User user;
                        user = new User();
                        user.setId(new Integer(editId.getEditText().getText().toString()));
                        user.setFullName(editFullName.getEditText().getText().toString());
                        user.setUserName(editUsername.getEditText().getText().toString());
                        user.seteMail(editEmail.getEditText().getText().toString());
                        user.setPassword(editPassword.getEditText().getText().toString());
                        user.setTipoUsuario(valueInt);
                        user.setEstado(0);
                        user.setProcontag(0);
                        String sintomas="ninguno";
                        user.setSintomas(sintomas);

                        Cursor res= myDb.findByEmail(editEmail.getEditText().getText().toString());
        if(res.getCount()==0) {
            boolean isInserted = myDb.insertData(user);
            if (isInserted) {
                Toast.makeText(SingUp_Form.this, "Data Insert", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login_From.class));
            }
            else
                Toast.makeText(SingUp_Form.this, "Data not Insert", Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(SingUp_Form.this, "Data not Insert User Exits", Toast.LENGTH_LONG).show();

        }
    }
}

        );
    }
    public void viewAll()
    {
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Cursor res=myDb.getAllData();
                        if(res.getCount()==0)
                        {
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                            buffer.append("Id: "+res.getString(0)+"\n");
                            buffer.append("Name: "+res.getString(1)+"\n");
                            buffer.append("UserName: "+res.getString(2)+"\n");
                            buffer.append("EMail: "+res.getString(3)+"\n");
                            buffer.append("Password: "+res.getString(4)+"\n");
                            buffer.append("TipoUsuario: "+res.getString(5)+"\n");
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
    public void onRadioButtonClicked(View view)
    {
        boolean checked =((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.radioButton_male:
                if(checked)
                    tipousuario=1;
                break;
            case R.id.radioButton_female:
                if(checked)
                    tipousuario=2;
                break;
        }
    }
}