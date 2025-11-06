package com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas;

import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReporteSalasGustadas {

       private Cine cine;
       private List<SalasGustadas> salasGustadas;

       public Cine getCine() {
              return cine;
       }

       public void setCine(Cine cine) {
              this.cine = cine;
       }

       public List<SalasGustadas> getSalasGustadas() {
              return salasGustadas;
       }

       public void setSalasGustadas(List<SalasGustadas> salasGustadas) {
              this.salasGustadas = salasGustadas;
       }

}
