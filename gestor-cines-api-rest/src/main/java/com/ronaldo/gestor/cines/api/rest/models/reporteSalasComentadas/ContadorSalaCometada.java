package com.ronaldo.gestor.cines.api.rest.models.reporteSalasComentadas;

/**
 *
 * @author ronaldo
 */
public class ContadorSalaCometada {

       private String codigoSala;
       private int vecesComentada;

       public String getCodigoSala() {
              return codigoSala;
       }

       public void setCodigoSala(String codigoSala) {
              this.codigoSala = codigoSala;
       }

       public int getVecesComentada() {
              return vecesComentada;
       }

       public void setVecesComentada(int vecesComentada) {
              this.vecesComentada = vecesComentada;
       }

       public void aumentarContador() {
              vecesComentada++;
       }

}
