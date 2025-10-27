package com.ronaldo.gestor.cines.api.rest.models.compraBoletos;

import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class CompraBoletos implements Editable {
       
       private static final int MINIMO=1;
       private static final int MAXIMO=5;
       
       private String idUsuario;
       private String codigoProyeccion;
       private LocalDate fechaCompra;
       private int cantidad;
       private double costoTotal;
       private double precioPorBoleto;
       
       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public String getCodigoProyeccion() {
              return codigoProyeccion;
       }

       public void setCodigoProyeccion(String codigoProyeccion) {
              this.codigoProyeccion = codigoProyeccion;
       }

       public LocalDate getFechaCompra() {
              return fechaCompra;
       }

       public void setFechaCompra(LocalDate fechaCompra) {
              this.fechaCompra = fechaCompra;
       }

       public int getCantidad() {
              return cantidad;
       }

       public void setCantidad(int cantidad) {
              this.cantidad = cantidad;
       }

       public double getCostoTotal() {
              return costoTotal;
       }

       public void setCostoTotal(int costoTotal) {
              this.costoTotal = costoTotal;
       }

       public double getPrecioPorBoleto() {
              return precioPorBoleto;
       }

       public void setPrecioPorBoleto(double precioPorBoleto) {
              this.precioPorBoleto = precioPorBoleto;
       }

       
        public boolean cuentaConSaldoDisponible(double saldoActual) {

              this.costoTotal = cantidad * precioPorBoleto;
              return saldoActual >= costoTotal;
       }

       public boolean datosValidos() {
              return StringUtils.isNotBlank(idUsuario)
                      && StringUtils.isNotBlank(codigoProyeccion)
                      && fechaCompra != null
                      && cantidad >= MINIMO && cantidad <= MAXIMO
                      && caracteresValidos();

       }

       private boolean caracteresValidos() {
              this.idUsuario = idUsuario.trim();
              this.codigoProyeccion = this.codigoProyeccion.trim();
              return true;
       }

}
