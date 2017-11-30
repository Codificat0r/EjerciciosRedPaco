package com.example.red;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class SubirFicheroActivity extends AppCompatActivity {

    private Button btnSubir;
    private TextView txvInfo;
    private EditText edtArchivo;
    private EditText edtContrasena;
    public final static String WEB = "http://192.168.0.227/acceso/upload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_fichero);

        btnSubir = (Button) findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subida();
            }
        });
        txvInfo = (TextView) findViewById(R.id.txvInfo);
        edtArchivo = (EditText) findViewById(R.id.edtArchivo);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
    }

    private void subida() {
        String fichero = edtArchivo.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(SubirFicheroActivity.this);
        File myFile;
        Boolean existe = true;
        myFile = new File(Environment.getExternalStorageDirectory(), fichero);
        //File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("fileToUpload", myFile);
            params.put("contrasena", edtContrasena.getText().toString());
        } catch (FileNotFoundException e) {
            existe = false;
            txvInfo.setText("Error en el fichero: " + e.getMessage());
            //Toast.makeText(this, "Error en el fichero: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (existe)
            RestClient.post(WEB, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    // called before request is started
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Conectando . . .");
                    //progreso.setCancelable(false);
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
                        public void onCancel(DialogInterface dialog){
                            RestClient.cancelRequests(getApplicationContext(), true);
                        }
                    });
                    progreso.show();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    // called when response HTTP status is "200 OK"
                    progreso.dismiss();
                    //Si el PHP manda un error, es un simple eco, pero se ha conseguido comunicar con el servidor
                    //por lo que será codigo 200, y se ejecutará el onSuccess.
                    txvInfo.setText("Server: " + response);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    progreso.dismiss();
                    txvInfo.setText("Error al subir el fichero: " + " CÓDIGO DE ERROR: " + statusCode + " CON EL SIGUIENTE MENSAJE: " + t.getMessage());
                }
            });
    }
}
