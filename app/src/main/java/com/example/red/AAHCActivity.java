package com.example.red;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class AAHCActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView web;
    private EditText direccion;
    private RadioButton radioJava;
    private RadioButton radioApache;
    private Button btnConectar;
    private TextView tiempo;

    private long tiempoInicio;
    private long tiempoFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aahc);

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
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View view) {

        if (view == btnConectar) {
            AAHC();
        }

    }

    private void AAHC() {
        final String texto = direccion.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(AAHCActivity.this);
        tiempoInicio = System.currentTimeMillis();
        RestClient.get(texto, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                //called before request is started
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                //progreso.setCancelable(false);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        RestClient.cancelRequests(getApplicationContext(), true);
                    }
                });
                progreso.show();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // called when response HTTP status is "200 OK"
                tiempoFin = System.currentTimeMillis();
                progreso.dismiss();
                web.loadDataWithBaseURL(null, response,"text/html", "UTF-8", null);
                tiempo.setText("Duración: " + String.valueOf(tiempoFin - tiempoInicio) + " milisegundos");
            }

            //Hay que poner los dos onFailure por si el servidor web decide devolverme el failure en array de Bytes o
            //en string.


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                tiempoFin = System.currentTimeMillis();
                progreso.dismiss();
                web.loadDataWithBaseURL(null, "Error: " + responseBytes.toString() + " ","text/html", "UTF-8", null);
                tiempo.setText("Duración: " + String.valueOf(tiempoFin - tiempoInicio) + " milisegundos");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                tiempoFin = System.currentTimeMillis();
                progreso.dismiss();
                web.loadDataWithBaseURL(null, "Error: " + response + " ","text/html", "UTF-8", null);
                tiempo.setText("Duración: " + String.valueOf(tiempoFin - tiempoInicio) + " milisegundos");
            }
        });
    }
}