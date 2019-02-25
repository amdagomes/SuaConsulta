package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ifpb.suaconsulta.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

    }

    public void carregaTelaMinhasConsultas(View view) {
        Intent intent = new Intent(MainActivity.this, MinhasConsultasActivity.class);
        startActivity(intent);
    }

    public void carregaSettingsConta(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsContaActivity.class);
        startActivity(intent);
    }

    public void carregaProcurarLocal(View view) {
        Intent intent = new Intent(MainActivity.this, LocalSaudeActivity.class);
        startActivity(intent);
    }

    public void carregaLembretes(View view) {
        Intent intent = new Intent(MainActivity.this, MedicamentosActivity.class);
        startActivity(intent);
    }
}
