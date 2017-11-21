package com.example.red;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;

public class DescargaImagenesActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUrl;
    Button btnVerImagen;
    Button btnDescargarImagen;
    ImageView imgvDescargada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_imagenes);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnVerImagen = (Button) findViewById(R.id.btnVerImagen);
        btnVerImagen.setOnClickListener(this);
        btnDescargarImagen = (Button) findViewById(R.id.btnDescargarImagen);
        btnDescargarImagen.setOnClickListener(this);
        imgvDescargada = (ImageView) findViewById(R.id.imgvDescargada);
    }
    @Override
    public void onClick(View v) {
        String url = edtUrl.getText().toString();
        if (v == btnVerImagen)
            descargaImagen(url);
        if (v == btnDescargarImagen)
            descargarFichero();
    }
    private void descargaImagen(String url) {
        /*Picasso.with(DescargaImagenesActivity.this)
                .load(edtUrl.getText().toString())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgvDescargada);*/
        //utilizar OkHttp3
        OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(DescargaImagenesActivity.this)
                .downloader(new OkHttp3Downloader(client))
                .build();

        picasso.with(DescargaImagenesActivity.this)
                .load(edtUrl.getText().toString())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgvDescargada);

    }

    public void descargarFichero() {
        final AsyncHttpClient cliente = new AsyncHttpClient();
        final ProgressDialog progreso = new ProgressDialog(this);
        final String[] splittedUrl = edtUrl.getText().toString().split("/");
        //File file = new File(Environment.getExternalStorageDirectory(), splittedUrl[splittedUrl.length - 1]);
        File myfile = new File(Environment.getExternalStorageDirectory(), "prueba.txt");
        cliente.get( edtUrl.getText().toString(), new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onStart() {
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Descargando fichero..." + edtUrl.getText().toString());
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        cliente.cancelRequests(getApplicationContext(), true);
                    }
                });
                progreso.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(DescargaImagenesActivity.this, "No se ha podido descargar el fichero. Código de error: " + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Toast.makeText(DescargaImagenesActivity.this, "Se ha descargado correctamente a " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                //file.renameTo(new File(file, splittedUrl[splittedUrl.length - 1]));
                progreso.dismiss();
            }
        });
    }
}

