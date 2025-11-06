package com.ronaldo.gestor.cines.api.rest.models.reportePeliculasProyectadas;

import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionResponse;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ProyeccionesSala {

       private Sala sala;
       private List<ProyeccionResponse> proyecciones;


       public String getNombreSala(){
              return sala.getNombre();
       }
       public void setSala(Sala sala) {
              this.sala = sala;
       }

       public List<ProyeccionResponse> getProyecciones() {
              return proyecciones;
       }

       public void setProyecciones(List<ProyeccionResponse> proyecciones) {
              this.proyecciones = proyecciones;
       }

}
