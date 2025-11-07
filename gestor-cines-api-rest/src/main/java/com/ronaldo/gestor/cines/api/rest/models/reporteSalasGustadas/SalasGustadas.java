package com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas;

import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoleto;
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
       private List<CompraBoleto> boletosComprados;

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

       public List<CompraBoleto> getBoletosComprados() {
              return boletosComprados;
       }

       public void setBoletosComprados(List<CompraBoleto> boletosComprados) {
              this.boletosComprados = boletosComprados;
       }

       public Sala getSala() {
              return sala;
       }
       
       public int getTotalBoletosVendidos(){
              int contador =0;
              for (int i = 0; i < boletosComprados.size(); i++) {
                     contador+=boletosComprados.get(i).getTotalBoletos();
              }
              return contador;
       }

}
