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
import com.ifpb.suaconsulta.model.Consulta;
import com.ifpb.suaconsulta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioFirebase {

    private static DatabaseReference usuarioRef = ConfiguracaoFirebase.getDatabaseReference().child("usuarios");
    private static List<Consulta> consultas = new ArrayList<>();

    public static void salvar(Usuario usuario){
        usuarioRef.child(usuario.getId()).setValue(usuario);
    }

    public static DatabaseReference getUsuariosRef(){
        return usuarioRef;
    }

    public static List<Consulta> getConsultas(){
        usuarioRef.child("consultas").getParent().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Consulta consulta = data.getValue(Consulta.class);
                    consulta.setUid(data.getKey());
                    consultas.add(consulta);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return consultas;
    }
}
