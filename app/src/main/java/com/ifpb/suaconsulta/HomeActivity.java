package com.ifpb.suaconsulta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

    }

    public void carregaTelaMinhasConsultas(View view) {
        Intent intent = new Intent(HomeActivity.this, MinhasConsultasActivity.class);
        startActivity(intent);
    }

    public void carregaSettingsConta(View view) {
        Intent intent = new Intent(HomeActivity.this, SettingsContaActivity.class);
        startActivity(intent);
    }

    public void carregaProcurarLocal(View view) {
        Intent intent = new Intent(HomeActivity.this, LocalSaudeActivity.class);
        startActivity(intent);
    }

    public void carregaLembretes(View view) {
        Intent intent = new Intent(HomeActivity.this, MedicamentosActivity.class);
        startActivity(intent);
    }
}
