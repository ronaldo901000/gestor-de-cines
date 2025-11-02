package com.ronaldo.gestor.cines.api.rest.models.pagosBloqueo;

import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class PagoBloqueo implements Editable {

       private static final int MAXIMO_BLOQUEO=30;
       private String codigoCine;
       private LocalDate fechaPago;
       private int totalDias;
       private int diasActivo;
       private double costo;
       private boolean activo;

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public LocalDate getFechaPago() {
              return fechaPago;
       }

       public void setFechaPago(LocalDate fechaPago) {
              this.fechaPago = fechaPago;
       }

       public int getTotalDias() {
              return totalDias;
       }

       public void setTotalDias(int totalDias) {
              this.totalDias = totalDias;
       }

       public int getDiasActivo() {
              return diasActivo;
       }

       public void setDiasActivo(int diasActivo) {
              this.diasActivo = diasActivo;
       }

       public double getCosto() {
              return costo;
       }

       public void setCosto(double costo) {
              this.costo = costo;
       }

       public boolean isActivo() {
              return activo;
       }

       public void setActivo(boolean activo) {
              this.activo = activo;
       }
       
       public boolean datosValidos(){
              return StringUtils.isNotBlank(codigoCine)
                      && fechaPago!=null
                      && totalDias>0 && totalDias<=MAXIMO_BLOQUEO;
       }

       public void calcularCostoTotal(double costoPorDia) {
              this.costo = costoPorDia * totalDias;
       }

       public boolean haySaldoSuficiente(double saldoActual) {
              return saldoActual >= costo;
       }
}
