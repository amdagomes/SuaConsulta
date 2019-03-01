package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.model.Usuario;

public class LoginActivity extends AppCompatActivity{

    private Button buttonLogin;
    private EditText textEmail;
    private EditText textSenha;
    private ProgressBar progressLogin;

    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLogin.setVisibility(View.VISIBLE);

                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    progressLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Preencha os campos", Toast.LENGTH_LONG).show();
                } else{
                    usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    validarLogin(usuario);
                }
            }
        });
    }

    public void verificarUsuarioLogado(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void validarLogin(Usuario usuario) {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressLogin.setVisibility(View.GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    progressLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Erro ao tentar fazer login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializarComponentes() {
        buttonLogin = findViewById(R.id.buttonLogin);
        textEmail = findViewById(R.id.editLoginEmail);
        textSenha = findViewById(R.id.editLoginSenha);
        progressLogin = findViewById(R.id.progressLogin);
    }

    public void carregaTelaCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }
}

