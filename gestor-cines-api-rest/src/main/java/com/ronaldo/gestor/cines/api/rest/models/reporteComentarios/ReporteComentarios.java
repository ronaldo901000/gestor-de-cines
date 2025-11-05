package com.ronaldo.gestor.cines.api.rest.models.reporteComentarios;

import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReporteComentarios {

       private Cine cine;
       private List<Comentario> comentarios;

       public String getNombreCine() {
              return cine.getNombre();
       }

       public String getUbicacion() {
              return cine.getUbicacion();
       }

       public void setCine(Cine cine) {
              this.cine = cine;
       }

       public List<Comentario> getComentarios() {
              return comentarios;
       }

       public void setComentarios(List<Comentario> comentarios) {
              this.comentarios = comentarios;
       }
}
