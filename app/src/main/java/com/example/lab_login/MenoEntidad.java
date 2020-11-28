package com.example.lab_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MenoEntidad extends AppCompatActivity {

    private String correo;
    private String contra;
    private EditText email;
    private EditText mensaje,estado;
    private Button enviar;
    private Button btnUsuario,bEstado,bContagios;
    private Session session;
    private LoginDatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meno_entidad);
        correo = "prueba16092020@gmail.com";
        contra = "prueba.2020";
        mensaje = findViewById(R.id.mensaje);
        enviar = findViewById(R.id.enviar);
        email = findViewById(R.id.correoU);
        estado = findViewById(R.id.estado);
        myDb = new LoginDatabaseHelper(this);
        bEstado = (Button) findViewById(R.id.acestado);
        btnUsuario = (Button) findViewById(R.id.usuario);
        bContagios =(Button) findViewById(R.id.consulta);
        viewAllU();
        aces();
        enviarcorreo();
        viewContagios();
    }
    public void enviarcorreo()
    {
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties propiedades = new Properties();
                propiedades.put("mail.smtp.host", "smtp.googlemail.com");
                propiedades.put("mail.smtp.socketFactory.port", "465");
                propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.put("mail.smtp.auth", "true");
                propiedades.put("mail.smtp.port", "465");

                try {
                    session = Session.getDefaultInstance(propiedades, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo,contra);
                        }
                    });

                    if (session != null) {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correo));
                        message.setSubject("Primera Prueba JAVA Mail");
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getText().toString()));
                        message.setContent(mensaje.getText().toString(), "text/html; charset=utf-8");

                        Transport.send(message);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        });

    }
    public void aces()
    {
        bEstado.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        String correos = email.getText().toString();
                        int estados= new Integer(estado.getText().toString());
                        myDb.actualizarestado(correos,estados);
                        showMessage("Ingreso","Nothing found");
                    }
                }
        );
    }

    public void viewContagios()
    {
        bContagios.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        int estados= new Integer(estado.getText().toString());

                        Cursor res=myDb.findEstados(estados);
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
                            buffer.append("Estado: "+res.getString(6)+"\n");
                            buffer.append("Probabilidad Contagio: "+res.getString(7)+"\n");
                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void viewAllU()
    {
        btnUsuario.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Cursor res=myDb.getAllDataU();
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
                            buffer.append("Sintomas: "+res.getString(8)+"\n");
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
}