package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioDao;
import com.ifpb.suaconsulta.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";
    private FirebaseAuth auth;
    private UsuarioDao usuarioDao;
    private Usuario usuarioLogado;
    DatabaseReference usuarioRef;
    private TextView textNome, textNumSus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        usuarioRef = UsuarioDao.getUsuariosRef();

        textNome = findViewById(R.id.textHomeNome);
        textNumSus = findViewById(R.id.textHomeNumSus);
        usuarioDao = new UsuarioDao();

        usuarioLogado = new Usuario();
        auth = ConfiguracaoFirebase.getFirebaseAuth();

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

    public void carregaTelaAgendarConsulta(View view){
        Intent intent = new Intent(MainActivity.this, AgendaConsulta.class);
        startActivity(intent);
    }

    private void setValores() {
        //seta valores dos text's view's
        textNome.setText(usuarioLogado.getNome());
        textNumSus.setText(usuarioLogado.getNumSus());
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaPreferences();
    }

    private void recuperaPreferences() {
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        usuarioLogado.setNome(preferences.getString("nome", "não definido"));
        usuarioLogado.setNumSus(preferences.getString("numSus", "não definido"));
        setValores();
    }
}
