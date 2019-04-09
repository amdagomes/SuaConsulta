package com.ifpb.suaconsulta.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioFirebase;
import com.ifpb.suaconsulta.helper.PreferenciasDoUsuario;
import com.ifpb.suaconsulta.model.Usuario;
import com.ifpb.suaconsulta.helper.Permissao;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarInformacoesActivity extends AppCompatActivity {

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";
    private FirebaseAuth auth;

    private static final int SELECAO_GALERIA = 200;
    private static final int SELECAO_CAMERA = 300;
    private SharedPreferences preferences;
    private EditText editNome, editCpf, editNumSus, editTelefone, editNascimento, editRua, editBairro, editEmail;
    private TextView editarFoto, editarFotoCamera;
    private CircleImageView imageEditarPerfil;
    private Spinner spinner;
    private ProgressBar progressBar;
    private Usuario usuario;

    private StorageReference storageReference;

    private ArrayAdapter<String> adapter;
    private String[] opcoesSpinner;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_editar_informacoes);

        //valida
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        Toolbar toolbar = findViewById(R.id.toolbarEditarInfo);
        toolbar.setTitle("Editar informações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        storageReference = ConfiguracaoFirebase.getStorageReference();

        preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        usuario = new Usuario();

        incializarComponentes();
        setComponentes();

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, SELECAO_GALERIA);
                }
            }
        });

        editarFotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if ( intent.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(intent, SELECAO_CAMERA );
                }
            }
        });

        opcoesSpinner = new String[]{"Feminino", "Masculino"};

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for ( int permissaoResultado : grantResults ){
            if ( permissaoResultado == PackageManager.PERMISSION_DENIED ){
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bitmap imagem = null;
            try{
                //seleção apenas da galeria de fotos
                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                }

                //caso tenha escolhido uma imagem
                if (imagem!=null){
                    imageEditarPerfil.setImageBitmap(imagem);

                    //Recupera dados da imagem para o firebase
                    ByteArrayOutputStream outStream =  new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, outStream);
                    byte[] dadosImagem = outStream.toByteArray();

                    //Salva imagem no firebase
                    StorageReference imagemRef = storageReference.child("imagensPerfil").child( preferences.getString("id", "naodefinido")+".jpeg");
                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditarInformacoesActivity.this, "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    atualizarFotoUsuario(uri);
                                }
                            });

                            Toast.makeText(EditarInformacoesActivity.this, "Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void atualizarFotoUsuario(Uri url) {
        PreferenciasDoUsuario.atualizarImagemPerfil(getApplicationContext(), url);
        usuario.setCaminhoFoto(url.toString());
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
        editarFoto = findViewById(R.id.textEditarFoto);
        editarFotoCamera = findViewById(R.id.textEditarCamera);
        imageEditarPerfil = findViewById(R.id.imageEditarPerfil);
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

        String url = preferences.getString("fotoPerfil", "");
        if (preferences.contains("fotoPerfil")){
            if (!url.equals("")){
                Uri urlFoto = Uri.parse(url);
                Glide.with(EditarInformacoesActivity.this)
                        .load(urlFoto)
                        .into(imageEditarPerfil);
            }
        }

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

        if(!preferences.getString("fotoPerfil", "").trim().isEmpty()){
            usuario.setCaminhoFoto(preferences.getString("fotoPerfil", ""));
        }

        if(preferences.contains("id")){
            usuario.setId(preferences.getString("id", "nao definido"));
            UsuarioFirebase.salvar(usuario);
        }else {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }

        PreferenciasDoUsuario.setarPreferencias(getApplicationContext(), usuario);
        progressBar.setVisibility(View.GONE);
    }

    private void recuperaUserAtual(){

        final DatabaseReference databaseReference = UsuarioFirebase.getUsuariosRef();
        databaseReference.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario oldUsuario = dataSnapshot.getValue(Usuario.class);
                Log.i("SALVAR_USUARIO", "ANTIGO: " + oldUsuario.toString());

                usuario.setEmail(oldUsuario.getEmail());
                usuario.setBairro(oldUsuario.getBairro());
                usuario.setCaminhoFoto(oldUsuario.getCaminhoFoto());
                usuario.setCpf(oldUsuario.getCpf());
                usuario.setRua(oldUsuario.getRua());
                usuario.setNome(oldUsuario.getNome());
                usuario.setDataNascimento(oldUsuario.getDataNascimento());
                usuario.setTelefone(oldUsuario.getTelefone());
                usuario.setNumSus(oldUsuario.getNumSus());
                usuario.setConsultas(oldUsuario.getConsultas());
                usuario.setSexo(oldUsuario.getSexo());
                usuario.setId(oldUsuario.getId());
//                usuario.setId(oldUsuario.getSenha());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.i("SALVAR_USUARIO", usuario.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaUserAtual();
    }
}
