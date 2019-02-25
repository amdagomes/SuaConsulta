package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.helper.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.helper.VerificaCPF;
import com.ifpb.suaconsulta.model.Usuario;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    private EditText textNome;
    private EditText textSus;
    private EditText textCpf;
    private EditText textNascimento;
    private EditText textTelefone;
    private EditText textEmail;
    private EditText textSenha;
    private ProgressBar progressCadastro;
    private Button buttonCadastrar;

    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cadastro);

        inicializaComponentes();

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressCadastro.setVisibility(View.VISIBLE);
                String nome = textNome.getText().toString();
                String numSus = textSus.getText().toString();
                String cpf = textCpf.getText().toString();
                String dataNascimento = textNascimento.getText().toString();
                String telefone = textTelefone.getText().toString();
                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if(nome.isEmpty() || numSus.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    progressCadastro.setVisibility(View.GONE);
                    Toast.makeText(CadastroActivity.this, "Preencha os campos com *", Toast.LENGTH_LONG).show();
                } else if (!VerificaCPF.isValid(cpf)){
                    progressCadastro.setVisibility(View.GONE);
                    Toast.makeText(CadastroActivity.this, "CPF inválido", Toast.LENGTH_SHORT).show();
                } else{
                    usuario = new Usuario();
                    usuario.setCpf(cpf);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        usuario.setDataNascimento(dataNascimento);
                    }
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    usuario.setNumSus(numSus);
                    usuario.setTelefone(telefone);
                    cadastrarUsuario(usuario);
                }
            }
        });
    }

    private void cadastrarUsuario(Usuario usuario) {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressCadastro.setVisibility(View.GONE);
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                    finish();
                } else {
                    progressCadastro.setVisibility(View.GONE);
                    String erroExcecao;
                    try {
                        throw task.getException();
                    }  catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Essa conta já foi cadastrada";
                    } catch (Exception e){
                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void inicializaComponentes() {
        textNome = findViewById(R.id.inputCadastroNome);
        textSus = findViewById(R.id.inputCadastroSUS);
        textCpf = findViewById(R.id.inputCadastroCPF);
        textNascimento = findViewById(R.id.inputCadastroNascimento);
        textTelefone = findViewById(R.id.inputCadastroTelefone);
        textEmail = findViewById(R.id.inputCadastroEmail);
        textSenha = findViewById(R.id.inputCadastroSenha);
        progressCadastro = findViewById(R.id.progressCadastro);
        buttonCadastrar = findViewById(R.id.button_cadastrar);
    }

    public void carregarTelaLogin(View view) {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
