package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ifpb.suaconsulta.R;

public class MedicamentosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_medicamentos);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarLembretes);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adiconar_lembrete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //trata do clique no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAdcLembrete:
                //ação
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void carregaTelaHome(View view) {
        Intent intent = new Intent(MedicamentosActivity.this, MainActivity.class);
        startActivity(intent);
    }
}