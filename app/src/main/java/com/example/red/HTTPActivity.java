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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class HTTPActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView web;
    private EditText direccion;
    private RadioButton radioJava;
    private RadioButton radioApache;
    private Button btnConectar;
    private TextView tiempo;

    private long tiempoInicio;
    private long tiempoFin;
    private boolean radioJavaState;

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
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }


    @Override
    public void onClick(View view) {
        String texto = direccion.getText().toString();
        long inicio, fin;
        Resultado resultado;
        switch (view.getId()) {
            case R.id.btnConectar:
                radioJavaState = radioJava.isChecked();
                Toast.makeText(this, "HOLA SOY EL BOTON", Toast.LENGTH_LONG).show();
                establecerConexion(texto);
                break;
        }
    }

    private void establecerConexion(String texto) {
        /*long inicio;
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
        tiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");*/
        tiempoInicio = System.currentTimeMillis();
        new TareaAsincrona().execute(new String[] {texto});
    }

    public class TareaAsincrona extends AsyncTask<String, Void, Resultado> {
        private ProgressDialog progreso;

        protected void onPreExecute() {
            progreso = new ProgressDialog(HTTPActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Conectando . . .");
            progreso.setCancelable(false);
            progreso.show();
        }

        //Aqui realizamos las tareas. No debemos acceder a los elementos, vistas desde aqui, como mucho en el onPreExecute y en
        //el onPostExecute. Si necesitamos aqui datos externos de elementos como el estado del radio button de java,
        //lo guardamos en antes de llamar a la tarea asincrona en una variable global. Hago lo mismo para los tiempos de
        //inicio de descarga de los datos del servidor web y de fin
        //para poder guardarlos desde cualquier lado y accederlos desde cualquier lado.
        protected Resultado doInBackground(String... cadena) {
            Resultado resultado;
            try {
                if (radioJavaState) {
                    resultado = Conectar.conectarJava(cadena[0]);
                } else {
                    resultado = Conectar.conectarApache(cadena[0]);
                }
            } catch (Exception e) {
                Log.e("HTTP", e.getMessage(), e);
                resultado = null;
                cancel(true);
            }
            return resultado;
        }



        //Debemos acceder a los elementos, a las vistas, desde el onPostExecute.
        protected void onPostExecute(Resultado result) {
            tiempoFin = System.currentTimeMillis();
            progreso.setMessage("Ha terminado la ejecución");
            progreso.dismiss();
            if (result.getCodigo())
                web.loadDataWithBaseURL(null, result.getContenido(),"text/html", "UTF-8", null);
            else
                web.loadDataWithBaseURL(null, result.getMensaje(),"text/html", "UTF-8", null);
            tiempo.setText("Duración: " + String.valueOf(tiempoFin - tiempoInicio) + " milisegundos");
        }



        protected void onCancelled() {
            progreso.setMessage("Se ha cancelado la ejecución");
            progreso.dismiss();
            // mostrar cancelación
        }
    }
}


