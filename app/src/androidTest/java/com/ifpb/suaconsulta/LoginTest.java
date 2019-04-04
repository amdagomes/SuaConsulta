package com.ifpb.suaconsulta;


import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.ifpb.suaconsulta.activity.LoginActivity;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.model.Usuario;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class LoginTest {

    LoginActivity login = new LoginActivity();
    Usuario usuario = new Usuario();

    @Test
    public void testLogin(){
        usuario.setEmail("admin@admin.com");
        usuario.setSenha("adminadmin");
        login.validarLogin(usuario);

        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();

       assertEquals(usuario.getEmail(), auth.getCurrentUser().getEmail());
    }
}
