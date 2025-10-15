package com.ronaldo.gestor.cines.api.rest.models.cines;

import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Cine {

       private String codigo;
       private String nombre;
       private String ubicacion;
       private LocalDate fechaCreacion;
       private double creditosCartera;
       private boolean activo;

       public Cine(String codigo, String nombre, String ubicacion, LocalDate fechaCreacion) {
              this.codigo = codigo;
              this.nombre = nombre;
              this.ubicacion = ubicacion;
              this.fechaCreacion = fechaCreacion;
       }

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }

       public String getUbicacion() {
              return ubicacion;
       }

       public void setUbicacion(String ubicacion) {
              this.ubicacion = ubicacion;
       }

       public LocalDate getFechaCreacion() {
              return fechaCreacion;
       }

       public void setFechaCreacion(LocalDate fechaCreacion) {
              this.fechaCreacion = fechaCreacion;
       }

       public double getCreditosCartera() {
              return creditosCartera;
       }

       public void setCreditosCartera(double creditosCartera) {
              this.creditosCartera = creditosCartera;
       }

       public boolean isActivo() {
              return activo;
       }

       public void setActivo(boolean activo) {
              this.activo = activo;
       }

       public boolean esValidoEnCampos() {
              return StringUtils.isNotBlank(codigo)
                      && StringUtils.isNotBlank(nombre)
                      && StringUtils.isNotBlank(ubicacion)
                      && fechaCreacion != null;
       }

}
