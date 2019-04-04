package com.ifpb.suaconsulta;

import com.ifpb.suaconsulta.helper.VerificaCPF;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
    @Test
    public void cpf_valido(){
        assertEquals(true, VerificaCPF.isValid("10770829406"));
    }
}