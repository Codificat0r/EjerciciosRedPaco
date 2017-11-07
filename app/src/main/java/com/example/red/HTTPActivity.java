package com.example.red;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class HTTPActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView web;
    private EditText direccion;
    private RadioButton radioJava;
    private RadioButton radioApache;
    private Button btnConectar;
    private TextView tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        iniciar();
    }

    private void iniciar() {
        direccion = (EditText) findViewById(R.id.edtUrl);
        radioJava = (RadioButton) findViewById(R.id.rbtnJava);
        radioApache = (RadioButton) findViewById(R.id.rbtnApache);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        web = (WebView) findViewById(R.id.wbvContenido);
        tiempo = (TextView) findViewById(R.id.txvTiempo);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }


    @Override
    public void onClick(View view) {
        String texto = direccion.getText().toString();
        long inicio, fin;
        Resultado resultado;
        switch (view.getId()) {
            case R.id.btnConectar:
                Toast.makeText(this, "HOLA SOY EL BOTON", Toast.LENGTH_LONG).show();
                establecerConexion(texto);
                break;
        }
    }

    private void establecerConexion(String texto) {
        long inicio;
        Resultado resultado;
        long fin;
        inicio = System.currentTimeMillis();
        if (radioJava.isChecked())
            resultado = Conectar.conectarJava(texto);
        else
            resultado = Conectar.conectarApache(texto);
        fin = System.currentTimeMillis();
        if (resultado.getCodigo())
            web.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
        else
            web.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
        tiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
    }
}
