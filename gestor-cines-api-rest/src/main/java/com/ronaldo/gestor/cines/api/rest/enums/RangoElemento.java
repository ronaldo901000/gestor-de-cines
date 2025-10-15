/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.enums;

/**
 *
 * @author ronaldo
 */
public enum RangoElemento {
       CINES(5);
       private int rango;

       private RangoElemento(int rango) {
              this.rango = rango;
       }

       public int getRango() {
              return rango;
       }
       
       

}
