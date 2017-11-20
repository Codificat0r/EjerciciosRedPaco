package com.example.red;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class DescargaImagenesActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUrl;
    Button btnVerImagen;
    ImageView imgvDescargada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_imagenes);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnVerImagen = (Button) findViewById(R.id.btnVerImagen);
        btnVerImagen.setOnClickListener(this);
        imgvDescargada = (ImageView) findViewById(R.id.imgvDescargada);
    }
    @Override
    public void onClick(View v) {
        String url = edtUrl.getText().toString();
        if (v == btnVerImagen)
            descargaImagen(url);
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
}

