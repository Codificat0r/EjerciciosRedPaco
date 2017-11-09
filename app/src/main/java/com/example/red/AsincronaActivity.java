package com.example.red;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * Lo que deberia contener esta clase lo he hecho en HTTPActivity, ya que me he equivocado, pero todavia
 * tiene el codigo comentado de la manera no asíncrona de acceder a descargar datos de un servidor web.
 */
public class AsincronaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asincrona);
    }
}
