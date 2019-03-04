package com.ifpb.suaconsulta.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta.model.Usuario;

public class UsuarioDao {

    private static DatabaseReference usuarioRef = ConfiguracaoFirebase.getDatabaseReference().child("usuarios");

    public static void salvar(Usuario usuario){
        usuarioRef.child(usuario.getId()).setValue(usuario);
    }

    public static DatabaseReference getUsuariosRef(){
        return usuarioRef;
    }
}
