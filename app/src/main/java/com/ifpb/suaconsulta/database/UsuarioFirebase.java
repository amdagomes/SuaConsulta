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
    private static FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
    private static final List<Consulta> consultas = new ArrayList<>();

    public static void salvar(Usuario usuario){
        usuarioRef.child(usuario.getId()).setValue(usuario);
    }

    public static DatabaseReference getUsuariosRef(){
        return usuarioRef;
    }

    public static List<Consulta> getConsultas(){
        usuarioRef.child(auth.getCurrentUser().getUid()).child("consultas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultas.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Consulta consulta = data.getValue(Consulta.class);
                    consulta.setUid(data.getKey());
                    consultas.add(consulta);
                    Log.i("AGENDA_MINHACONSULTA", consulta.toString());
                }
                Log.i("AGENDA_", "tamanho dentro: "+ consultas.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.i("AGENDA_", "tamanho: "+ consultas.size());
        return consultas;
    }

}
