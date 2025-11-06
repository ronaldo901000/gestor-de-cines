package com.ronaldo.gestor.cines.api.rest.models.reportePeliculasProyectadas;

import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReportePeliculasProyectadas {

       private Cine cine;
       private List<ProyeccionesSala> salas;

       public Cine getCine() {
              return cine;
       }

       public void setCine(Cine cine) {
              this.cine = cine;
       }

       public List<ProyeccionesSala> getInfoSalas() {
              return salas;
       }

       public void setSalas(List<ProyeccionesSala> salas) {
              this.salas = salas;
       }

}
