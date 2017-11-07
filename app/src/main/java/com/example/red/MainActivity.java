package com.example.red;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnConexionHTTP;
    private Button btnConexionAsincrona;
    private Button btnConexionAndroidAsincronaHTTPClient;
    private Button btnConexionVolley;
    private Button btnDescargaImagenes;
    private Button btnSubirArchivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConexionHTTP = (Button)findViewById(R.id.btnConexionHTTP);
        btnConexionHTTP.setOnClickListener(this);
        btnConexionAsincrona = (Button)findViewById(R.id.btnConexionAsincrona);
        btnConexionAsincrona.setOnClickListener(this);
        btnConexionAndroidAsincronaHTTPClient = (Button)findViewById(R.id.btnConexionAndroidAsincronaHTTPClient);
        btnConexionAndroidAsincronaHTTPClient.setOnClickListener(this);
        btnConexionVolley = (Button)findViewById(R.id.btnConexionVolley);
        btnConexionVolley.setOnClickListener(this);
        btnDescargaImagenes = (Button)findViewById(R.id.btnDescargaImagenes);
        btnDescargaImagenes.setOnClickListener(this);
        btnSubirArchivos = (Button)findViewById(R.id.btnSubirArchivos);
        btnSubirArchivos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnConexionHTTP:
                i = new Intent(this, HTTPActivity.class);
                startActivity(i);
                break;
            case R.id.btnConexionAsincrona:
                i = new Intent(this, AsincronaActivity.class);
                startActivity(i);
                break;
            case R.id.btnConexionAndroidAsincronaHTTPClient:
                i = new Intent(this, AAHCActivity.class);
                startActivity(i);
                break;
            case R.id.btnConexionVolley:
                i = new Intent(this, VolleyActivity.class);
                startActivity(i);
                break;
            case R.id.btnDescargaImagenes:
                i = new Intent(this, DescargaImagenesActivity.class);
                startActivity(i);
                break;
            case R.id.btnSubirArchivos:
                i = new Intent(this, SubirFicheroActivity.class);
                startActivity(i);
                break;

        }
    }
}
