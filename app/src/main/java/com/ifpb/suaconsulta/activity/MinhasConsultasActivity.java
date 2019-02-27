package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ifpb.suaconsulta.R;

public class MinhasConsultasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_minhas_consultas);

        Toolbar toolbar = findViewById(R.id.toolbarMinhasConsultas);
        toolbar.setTitle("Minhas Consultas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void carregaTelaHome(View view){
        Intent intent = new Intent(MinhasConsultasActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
