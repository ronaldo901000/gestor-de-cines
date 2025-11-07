package com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos;

import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class CompraBoleto {

       private String idUsario;
       private LocalDate fecha;
       private int totalBoletos;
       private double costoTotal;

       public String getIdUsario() {
              return idUsario;
       }

       public void setIdUsario(String idUsario) {
              this.idUsario = idUsario;
       }

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fehca) {
              this.fecha = fehca;
       }

       public int getTotalBoletos() {
              return totalBoletos;
       }

       public void setTotalBoletos(int totalBoletos) {
              this.totalBoletos = totalBoletos;
       }

       public double getCostoTotal() {
              return costoTotal;
       }

       public void setCostoTotal(double costoTotal) {
              this.costoTotal = costoTotal;
       }

}
