package com.ronaldo.gestor.cines.api.rest.models.reporteGanancias;

/**
 *
 * @author ronaldo
 */
public class BalanceFinanciero {

       private double costos;
       private double ingresos;
       private double ganancia;

       public double getCostos() {
              return costos;
       }

       public void setCostos(double costos) {
              this.costos = costos;
       }

       public double getIngresos() {
              return ingresos;
       }

       public void setIngresos(double ingresos) {
              this.ingresos = ingresos;
       }

       public double getGanancia() {
              return ganancia;
       }

       public void setGanancia(double ganancia) {
              this.ganancia = ganancia;
       }
       
       public void calcularGanancia(){
              this.ganancia=ingresos-costos;
       }

}
