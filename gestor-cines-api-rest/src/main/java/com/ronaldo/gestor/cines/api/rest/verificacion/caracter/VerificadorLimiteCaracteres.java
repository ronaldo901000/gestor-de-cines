/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.verificacion.caracter;

/**
 *
 * @author ronaldo
 */
public class VerificadorLimiteCaracteres {

       public boolean numeroCaracteresValido(String elemento, int limiteCaracter) {
              return elemento.length() <= limiteCaracter;
       }
}
