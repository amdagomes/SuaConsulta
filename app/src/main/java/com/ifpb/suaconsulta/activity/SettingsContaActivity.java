package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioDao;
import com.ifpb.suaconsulta.model.Usuario;

public class SettingsContaActivity extends AppCompatActivity {

    private final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";
    private FirebaseAuth auth;
    private DatabaseReference usuarioRef;
    private Usuario usuarioLogado;
    TextView nome, cpf, numSus, sexo, telefone, email, rua, bairro, dataNascimento;

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

       usuarioRef = UsuarioDao.getUsuariosRef();
       usuarioLogado = new Usuario();

       incializarComponentes();

        auth = ConfiguracaoFirebase.getFirebaseAuth();
    }

    private void incializarComponentes() {
        nome = findViewById(R.id.tv_settings_nome);
        cpf = findViewById(R.id.tv_settings_cpf);
        dataNascimento = findViewById(R.id.tv_settings_aniversario);
        rua = findViewById(R.id.tv_settings_rua);
        bairro = findViewById(R.id.tv_settings_bairro);
        email = findViewById(R.id.tv_settings_email);
        sexo = findViewById(R.id.tv_settings_sexo);
        telefone = findViewById(R.id.tv_settings_telefone);
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
                startActivity(new Intent(SettingsContaActivity.this, EditarInformacoes.class));
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

    private void setValores() {
        nome.setText(usuarioLogado.getNome());
        email.setText(usuarioLogado.getEmail());
        cpf.setText(usuarioLogado.getCpf());
        dataNascimento.setText(usuarioLogado.getDataNascimento());
        bairro.setText(usuarioLogado.getBairro());
        rua.setText(usuarioLogado.getRua());
        telefone.setText(usuarioLogado.getTelefone());
        Log.i("RESULTADO", usuarioLogado.getSexo());
        sexo.setText(usuarioLogado.getSexo());
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

    private void recuperaPreferences() {
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        usuarioLogado.setNome(preferences.getString("nome", "não definido"));
        usuarioLogado.setNumSus(preferences.getString("numSus", "não definido"));
        usuarioLogado.setCpf(preferences.getString("cpf", "não definido"));
        usuarioLogado.setTelefone(preferences.getString("telefone", "não definido"));
        usuarioLogado.setRua(preferences.getString("rua", "não definido"));
        usuarioLogado.setBairro(preferences.getString("bairro", "não definido"));
        usuarioLogado.setDataNascimento(preferences.getString("dataNascimento", "não definido"));
        usuarioLogado.setEmail(preferences.getString("email", "não definido"));
        usuarioLogado.setSexo(preferences.getString("sexo", "não definido"));
        setValores();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaPreferences();
    }
}
