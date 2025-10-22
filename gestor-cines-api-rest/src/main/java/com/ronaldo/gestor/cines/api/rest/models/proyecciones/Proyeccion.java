package com.ronaldo.gestor.cines.api.rest.models.proyecciones;

import com.ronaldo.gestor.cines.api.rest.enums.caracter.LimiteCaracter;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Proyeccion implements Editable{

       private static final int PRECIO_MINIMO = 0;
       private static final int PRECIO_MAXIMO = 100000000;
       private String codigo;
       private String codigoPelicula;
       private String codigoSala;
       private LocalDate fecha;
       private LocalTime horaInicio;
       private LocalTime horaFin;
       private double precio;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getCodigoPelicula() {
              return codigoPelicula;
       }

       public void setCodigoPelicula(String codigoPelicula) {
              this.codigoPelicula = codigoPelicula;
       }

       public String getCodigoSala() {
              return codigoSala;
       }

       public void setCodigoSala(String codigoSalon) {
              this.codigoSala = codigoSalon;
       }

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fecha) {
              this.fecha = fecha;
       }

       public LocalTime getHoraInicio() {
              return horaInicio;
       }

       public void setHoraInicio(LocalTime horaInicio) {
              this.horaInicio = horaInicio;
       }

       public LocalTime getHoraFin() {
              return horaFin;
       }

       public void setHoraFin(LocalTime horaFin) {
              this.horaFin = horaFin;
       }

       public double getPrecio() {
              return precio;
       }

       public void setPrecio(double precio) {
              this.precio = precio;
       }

       public boolean datosValidos() {
              return StringUtils.isNotBlank(codigo)
                      && StringUtils.isNotBlank(codigoPelicula)
                      && StringUtils.isNotBlank(codigoSala)
                      && fecha != null
                      && horaInicio != null
                      && horaFin != null
                      && precioValido()
                      && caracteresValidos(codigo, LimiteCaracter.CODIGO.get());

       }

       private boolean precioValido() {
              return precio >= PRECIO_MINIMO && precio <= PRECIO_MAXIMO;
       }

       private boolean caracteresValidos(String cadena, int limiteCaracteres) {
              VerificadorCaracteres verificador = new VerificadorCaracteres();

              return verificador.caracteresPermitidosNombre(cadena) && verificador.numeroCaracteresValido(cadena, limiteCaracteres);
       }
}
