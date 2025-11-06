package com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas;

import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class SalasGustadas {

       private Sala sala;
       private List<Calificacion> calificaciones;
       private double promedio;

       public String getNombreSala() {
              return sala.getNombre();
       }

       public void setSala(Sala sala) {
              this.sala = sala;
       }

       public List<Calificacion> getCalificaciones() {
              return calificaciones;
       }

       public void setCalificaciones(List<Calificacion> calificaciones) {
              this.calificaciones = calificaciones;
       }

       public double calcularPromedioCalificacion() {
              double total = 0;
              if (!calificaciones.isEmpty()) {
                     for (int i = 0; i < calificaciones.size(); i++) {
                            total += calificaciones.get(i).getCalificacion();
                     }
                     return total / calificaciones.size();
              }
              return 0;
       }

       public double getPromedio() {
              return promedio;
       }

       public void setPromedio(double promedio) {
              this.promedio = promedio;
       }
       
       
}
