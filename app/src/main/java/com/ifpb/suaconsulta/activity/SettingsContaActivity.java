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

public class SettingsContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings_conta);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //trata clique do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemEditar:
                //ação
                break;
            case R.id.itemAlterarSenha:
                //ação
                break;
            case R.id.itemSair:
                //ação
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void carregaTelaHome(View view) {
        Intent intent = new Intent(SettingsContaActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
