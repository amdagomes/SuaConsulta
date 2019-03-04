package com.ifpb.suaconsulta.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.UsuarioDao;
import com.ifpb.suaconsulta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EditarInformacoes extends AppCompatActivity {

    private final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";
    private SharedPreferences preferences;
    private EditText editNome, editCpf, editNumSus, editTelefone, editNascimento, editRua, editBairro, editEmail;
    private Spinner spinner;
    private ProgressBar progressBar;
    private Usuario usuario;

    private ArrayAdapter<String> adapter;
    private List<String> opcoesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_editar_informacoes);

        Toolbar toolbar = findViewById(R.id.toolbarEditarInfo);
        toolbar.setTitle("Editar informações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        usuario = new Usuario();

        incializarComponentes();
        setComponentes();

        opcoesSpinner = new ArrayList<>();
        opcoesSpinner.add("Feminino");
        opcoesSpinner.add("Masculino");

        //seta valores do spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opcoesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //ação de clique no spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        usuario.setSexo("Feminino");
                        break;
                    case 1:
                        usuario.setSexo("Masculino");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void incializarComponentes() {
        editBairro = findViewById(R.id.editEditarBairro);
        editEmail = findViewById(R.id.editEditarEmail);
        editCpf = findViewById(R.id.editeditarCpf);
        editNumSus = findViewById(R.id.editEditarNumSus);
        editNome = findViewById(R.id.editEditarNome);
        editRua = findViewById(R.id.editEditarRua);
        editTelefone = findViewById(R.id.editEditarTelefone);
        editNascimento = findViewById(R.id.editEditarNascimento);
        progressBar = findViewById(R.id.progressBarEditarInfo);
        spinner = findViewById(R.id.spinnerEditar);
        progressBar.setVisibility(View.GONE);
    }


    private void setComponentes() {
        editNome.setText(preferences.getString("nome", "não definido"));
        editEmail.setText(preferences.getString("email", "não definido"));
        editCpf.setText(preferences.getString("cpf", "não definido"));
        editNascimento.setText(preferences.getString("dataNascimento", "não definido"));
        editBairro.setText(preferences.getString("bairro", "não definido"));
        editRua.setText(preferences.getString("rua", "não definido"));
        editTelefone.setText(preferences.getString("telefone", "não definido"));
        editNumSus.setText(preferences.getString("numSus", "não definido"));

        if(preferences.getString("sexo", "não definido").equals("Feminino")){
            spinner.setSelection(0);
        }else {
            spinner.setSelection(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_informaoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvarInformacoes:
                progressBar.setVisibility(View.VISIBLE);
                //atualiza informações do usuário
                salvarUsuario();
                finish();
                Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarUsuario() {
        usuario.setEmail(editEmail.getText().toString());
        usuario.setCpf(editCpf.getText().toString());
        usuario.setNumSus(editNumSus.getText().toString());
        usuario.setTelefone(editTelefone.getText().toString());
        usuario.setDataNascimento(editNascimento.getText().toString());
        usuario.setNome(editNome.getText().toString());
        usuario.setRua(editRua.getText().toString());
        usuario.setBairro(editBairro.getText().toString());

        if(preferences.contains("id")){
            usuario.setId(preferences.getString("id", "nao definido"));
            UsuarioDao.salvar(usuario);
        }else {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }

        setarPreferencias();
    }

    private void setarPreferencias() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nome", usuario.getNome());
        editor.putString("numSus", usuario.getNumSus());
        editor.putString("telefone", usuario.getTelefone());
        editor.putString("dataNascimento", usuario.getDataNascimento());
        editor.putString("rua", usuario.getRua());
        editor.putString("bairro", usuario.getBairro());
        editor.putString("sexo", usuario.getSexo());
        editor.apply();

        progressBar.setVisibility(View.GONE);
    }
}
