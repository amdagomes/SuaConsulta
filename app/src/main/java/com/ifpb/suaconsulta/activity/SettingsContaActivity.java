package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;

public class SettingsContaActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       setContentView(R.layout.activity_settings_conta);

       Toolbar toolbar = findViewById(R.id.toolbarSettings);
       toolbar.setTitle("Conta");
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
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
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        try{
            auth.signOut();
            startActivity(new Intent(SettingsContaActivity.this, LoginActivity.class));
            finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
