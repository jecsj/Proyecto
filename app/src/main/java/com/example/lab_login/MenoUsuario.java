package com.example.lab_login;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import javax.mail.Session;

public class MenoUsuario extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    private TextInputLayout editId;
    private LoginDatabaseHelper miBD;
    TextView tDireccion,tEstado,tProbabilidad;
    Button bGuardar,blistar,bEstado,bProbabilidad;


    private EditText mensaje,email;
    private Button enviar,mBtinAnadir;
    private ListView mListView;
    private EditText mEditText;
    private List<String> mLista =new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meno_usuario);
        tDireccion = (TextView) findViewById(R.id.txtDireccion);
        tEstado = (TextView) findViewById(R.id.txtEstado);
        tProbabilidad = (TextView) findViewById(R.id.txtProbabilidad);
        bEstado =(Button)findViewById(R.id.btnTraerestado);
        bGuardar =(Button)findViewById(R.id.btnGuardar);
        bProbabilidad =(Button)findViewById(R.id.btnTraerprobabilidad);
        enviar = findViewById(R.id.btnEnviar);

        mBtinAnadir=findViewById(R.id.btnAgregar);
        mBtinAnadir.setOnClickListener(this);
        mListView=findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        mEditText=findViewById(R.id.etLista);

        editId=(TextInputLayout)findViewById(R.id.editText_id);

        miBD = new LoginDatabaseHelper(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        bEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id2= new Integer(editId.getEditText().getText().toString());

                    estado(id2);

            }
        });
        bProbabilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String datosDireccion = tDireccion.getText().toString();

                String sDate = c.get(Calendar.YEAR) + "-"
                        + c.get(Calendar.MONTH)
                        + "-" + c.get(Calendar.DAY_OF_MONTH);

                int sDate2 = c.get(Calendar.HOUR_OF_DAY);

                probacontagio(datosDireccion,sDate,sDate2);

            }
        });
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datosDireccion = tDireccion.getText().toString();
                int id2= new Integer(editId.getEditText().getText().toString());

                if(tDireccion.length()!=0){
                    Calendar c = Calendar.getInstance();

                    String sDate = c.get(Calendar.YEAR) + "-"
                            + c.get(Calendar.MONTH)
                            + "-" + c.get(Calendar.DAY_OF_MONTH);

                    int sDate2 = c.get(Calendar.HOUR_OF_DAY);

                    agregar(id2,datosDireccion,sDate,sDate2);
                    estado(id2);
                }else{
                    Toast.makeText(MenoUsuario.this,"Ingresa algo",Toast.LENGTH_LONG).show();
                }
            }
        });
        aces();
    }

    public  void agregar(int nuevaEntrada,String nuevaEntrada2, String sDate,int sDate2){
        boolean insertarData = miBD.addData(nuevaEntrada,nuevaEntrada2,sDate,sDate2);
        if(insertarData == true) {
            Toast.makeText(this,"Datos insertados correctamente",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Algo salio mal",Toast.LENGTH_LONG).show();
        }
    }

    private void probacontagio(String direccion,String fecha,int hora)
    {

        Cursor res=miBD.getProbabilidad(direccion,fecha,hora);
        int contadorestado0=0;
        int contadorestado1=0;
        int contadorestado2=0;
        if(res.getCount()==0)
        {
            return;
        }
        while(res.moveToNext())
        {
            if(res.getString(0).equals("0"))
            {
                contadorestado0=contadorestado0+1;
            }

            if(res.getString(0).equals("1"))
            {
                contadorestado1=contadorestado1+1;
            }

            if(res.getString(0).equals("2"))
            {
                contadorestado2=contadorestado2+1;
            }

        }

        int contadortotal=contadorestado0+contadorestado1+contadorestado2;
         double probabilidad= ((contadorestado1+contadorestado2)*100)/contadortotal;
        tProbabilidad.setText("Probabilidad: "+probabilidad+"\n");
        int id2= new Integer(editId.getEditText().getText().toString());

        miBD.actualizarprobabilidad(id2,probabilidad);

    }


    private void estado(int id2)
    {
        Cursor res=miBD.getEstado(id2);
        if(res.getCount()==0)
        {
            return;
        }
        while(res.moveToNext())
        {
            if(res.getString(0).equals("0"))
            {

                tEstado.setText("Estado: "+res.getString(0)+"\n");
            }

            if(res.getString(0).equals("1"))
            {

                tEstado.setText("Posible contagio: "+res.getString(0)+"\n");
            }

            if(res.getString(0).equals("2"))
            {

                tEstado.setText("Contagiado: "+res.getString(0)+"\n");
            }
               tProbabilidad.setText("Probabilidad: "+res.getString(1)+"\n");
        }

    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMenoUsuario(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        tDireccion.setText("Localización agregada");
        tDireccion.setText("");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tDireccion.setText("Mi direccion es:"
                            + DirCalle.getAddressLine(0) + "\n Longitud: "+loc.getLongitude()+ "\n Latitud: "+loc.getLatitude());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
       MenoUsuario menoUsuario;
        public MenoUsuario getMenoUsuario() {
            return menoUsuario;
        }
        public void setMenoUsuario(MenoUsuario menoUsuario) {
            this.menoUsuario = menoUsuario;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();

            this.menoUsuario.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            tDireccion.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            tDireccion.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
    public void aces()
    {
        enviar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        int id2= new Integer(editId.getEditText().getText().toString());
                        String sintomas="Sintomas ";
                        for (int i=0;i<mLista.size();i++)
                        {
                            sintomas=sintomas+", "+mLista.get(i);
                        }
                        miBD.actualizarsintomas(id2,sintomas);
                        showMessage("Enviar sintomas","Nothing found");
                    }
                }
        );
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAgregar: String texto = mEditText.getText().toString().trim();
                mLista.add(texto);
                mEditText.getText().clear();
                mAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mLista);
                mListView.setAdapter(mAdapter);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this,"Item Clicked:"+i,Toast.LENGTH_SHORT).show();
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