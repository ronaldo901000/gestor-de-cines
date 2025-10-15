/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.verificacion.caracter;

/**
 *
 * @author ronaldo
 */
public class VerificadorCaracteres {

       public boolean numeroCaracteresValido(String elemento, int limiteCaracter) {
              return elemento.length() <= limiteCaracter;
       }

       /**
        *
        * @param texto
        * @return
        */
       public boolean caracteresPermitidosNombre(String texto) {
              for (int i = 0; i < texto.length(); i++) {
                     char c = texto.charAt(i);
                     if (!Character.isLetter(c)
                             && !Character.isDigit(c)
                             && c != '-'
                             && c != ' '
                             && c != ','
                             && c != '.') {
                            return false;
                     }
              }
              return true;
       }

}
