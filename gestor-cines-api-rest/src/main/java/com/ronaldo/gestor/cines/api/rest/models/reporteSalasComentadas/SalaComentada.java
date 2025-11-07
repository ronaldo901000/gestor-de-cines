package com.ronaldo.gestor.cines.api.rest.models.reporteSalasComentadas;

import com.ronaldo.gestor.cines.api.rest.models.opinion.Opinion;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class SalaComentada {

       private Sala sala;
       private List<Opinion> opiniones;

       public Sala getSala() {
              return sala;
       }

       public String getNombreSala() {
              return sala.getNombre();
       }
       
       public String getNombreCine(){
              return sala.getNombreCine();
       }
       
       public int getTotalComentarios(){
              return opiniones.size();               
       }

       public void setSala(Sala sala) {
              this.sala = sala;
       }

       public List<Opinion> getOpiniones() {
              return opiniones;
       }

       public void setOpiniones(List<Opinion> opiniones) {
              this.opiniones = opiniones;
       }

}
