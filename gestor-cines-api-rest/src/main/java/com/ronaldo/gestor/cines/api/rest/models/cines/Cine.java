package com.ronaldo.gestor.cines.api.rest.models.cines;

import com.ronaldo.gestor.cines.api.rest.enums.caracter.LimiteCaracter;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Cine implements Editable{

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
              codigo = codigo.trim();
              nombre = nombre.trim();
              ubicacion = ubicacion.trim();
              return StringUtils.isNotBlank(codigo)
                      && StringUtils.isNotBlank(nombre)
                      && StringUtils.isNotBlank(ubicacion)
                      && fechaCreacion != null
                      && limiteCaracteresValidos()
                      && caracteresPermitidos();
       }

       private boolean limiteCaracteresValidos() {
              VerificadorCaracteres verificador = new VerificadorCaracteres();

              return !(!verificador.numeroCaracteresValido(codigo, LimiteCaracter.CODIGO.get())
                      || !verificador.numeroCaracteresValido(nombre, LimiteCaracter.NOMBRE_CINE.get())
                      || !verificador.numeroCaracteresValido(ubicacion, LimiteCaracter.UBICACION_CINE.get()));
       }

       private boolean caracteresPermitidos() {
              VerificadorCaracteres verificador = new VerificadorCaracteres();
              return !(!verificador.caracteresPermitidosNombre(codigo) || !verificador.caracteresPermitidosNombre(nombre));
       }

}
