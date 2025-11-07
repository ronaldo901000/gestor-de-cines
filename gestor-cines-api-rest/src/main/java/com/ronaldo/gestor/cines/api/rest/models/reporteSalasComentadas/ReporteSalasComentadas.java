package com.ronaldo.gestor.cines.api.rest.models.reporteSalasComentadas;

import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReporteSalasComentadas {

       List<SalaComentada> salasComentadas;

       public List<SalaComentada> getSalasComentadas() {
              return salasComentadas;
       }

       public void setSalasComentadas(List<SalaComentada> salasComentadas) {
              this.salasComentadas = salasComentadas;
       }

}
