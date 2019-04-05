package com.ifpb.suaconsulta.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioFirebase;
import com.ifpb.suaconsulta.model.Usuario;

import static android.content.Context.MODE_PRIVATE;

public class PreferenciasDoUsuario {

    private  static FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
    private static final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";

    public static void recuperarUsuarioLogado(final Context context) {
        DatabaseReference referenceUsuario = UsuarioFirebase.getUsuariosRef();
        referenceUsuario.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuarioLogado = dataSnapshot.getValue(Usuario.class);
                setarPreferencias(context, usuarioLogado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setarPreferencias(Context context, Usuario usuarioLogado) {

        SharedPreferences preferences = context.getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nome", usuarioLogado.getNome());
        editor.putString("numSus", usuarioLogado.getNumSus());
        editor.putString("cpf", usuarioLogado.getCpf());
        editor.putString("email", usuarioLogado.getEmail());
        editor.putString("telefone", usuarioLogado.getTelefone());
        editor.putString("dataNascimento", usuarioLogado.getDataNascimento());
        editor.putString("rua", usuarioLogado.getRua());
        editor.putString("bairro", usuarioLogado.getBairro());
        editor.putString("sexo", usuarioLogado.getSexo());
        editor.putString("id", usuarioLogado.getId());
        editor.putString("fotoPerfil", usuarioLogado.getCaminhoFoto());

        //editor.commit();
        editor.apply();
    }

    public static void atualizarImagemPerfil(Context context, Uri url){
        SharedPreferences preferences = context.getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fotoPerfil", url.toString());
        editor.apply();
    }

}
