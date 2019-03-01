package com.ifpb.suaconsulta.database;

import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta.model.Usuario;

public class UsuarioDao {

    private DatabaseReference usuarioRef;

    public UsuarioDao() {
        this.usuarioRef = ConfiguracaoFirebase.getDatabaseReference().child("usuarios");
    }

    public void salvar(Usuario usuario){
        DatabaseReference usuariosRef = usuarioRef.child(usuario.getId());
        usuariosRef.setValue(usuario);
    }

}
