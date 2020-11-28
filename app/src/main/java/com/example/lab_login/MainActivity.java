package com.example.lab_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<Entidad> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems=(ListView)findViewById(R.id.lvItems);
        adaptador=new Adaptador(this,GetArrayItems());
        lvItems.setAdapter(adaptador);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.

                //  Log.i("Click", "click en el elemento " + position + " de mi ListView");
                if(position==0)
                {
                    startActivity(new Intent(getApplicationContext(),Login_From.class));

               //     showMessage("Data","Ingresar como Usuario " + position + " de mi ListView");

                }
                if(position==1)
                {

                    startActivity(new Intent(getApplicationContext(),Login_From.class));

                //    showMessage("Data","Ingresar  como Entidad" + position + " de mi ListView");

                }
                if(position==2)
                {

                    startActivity(new Intent(getApplicationContext(),SingUp_Form.class));

                }
               }
        });
    }

    private ArrayList<Entidad> GetArrayItems(){

        listItems.add(new Entidad(R.drawable.imagen1,"Usuario","Acceder como usuario"));
        listItems.add(new Entidad(R.drawable.imagen2,"Entidad","Acceder como Entidad"));
        listItems.add(new Entidad(R.drawable.imagen,"Registrarse","Acceder para Registrarse"));
        return listItems;
    }

    public void showMessage(String tittle, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.show();
    }
}