package com.ronaldo.gestor.cines.api.rest.models.opinion;

import com.ronaldo.gestor.cines.api.rest.enums.caracter.LimiteCaracter;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Opinion implements Editable {

       private static final int CALIFICACION_MIMIMA = 1;
       private static final int CALIFICACION_MAXIMA = 5;
       private String id;
       private String codigoEntidad;
       private String idUsuario;
       private String comentario;
       private int calificacion;
       private LocalDate fecha;
       private String tipo;

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }

       public String getCodigoEntidad() {
              return codigoEntidad;
       }

       public void setCodigoEntidad(String codigoEntidad) {
              this.codigoEntidad = codigoEntidad;
       }

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public String getComentario() {
              return comentario;
       }

       public void setComentario(String comentario) {
              this.comentario = comentario;
       }

       public int getCalificacion() {
              return calificacion;
       }

       public void setCalificacion(int calificacion) {
              this.calificacion = calificacion;
       }

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fecha) {
              this.fecha = fecha;
       }

       public String getTipo() {
              return tipo;
       }

       public void setTipo(String tipo) {
              this.tipo = tipo;
       }

       public boolean datosValidos() {
              return StringUtils.isNotBlank(codigoEntidad)
                      && StringUtils.isNotBlank(idUsuario)
                      && StringUtils.isNotBlank(comentario)
                      && StringUtils.isNotBlank(tipo)
                      && tipoValido()
                      && calificacionValida()
                      && fecha != null
                      && caracteresValidos(comentario, LimiteCaracter.DESCRIPCION_ANUNCIO.get());

       }

       private boolean calificacionValida() {
              return calificacion >= CALIFICACION_MIMIMA && calificacion <= CALIFICACION_MAXIMA;
       }

       public boolean tipoValido() {
              return tipo != null &&
           (tipo.equals(TipoOpinion.SALA.name()) || tipo.equals(TipoOpinion.PELICULA.name()));
       }

       private boolean caracteresValidos(String cadena, int maximoCaracteres) {
              VerificadorCaracteres verificador = new VerificadorCaracteres();

              return verificador.numeroCaracteresValido(cadena, maximoCaracteres)
                      && verificador.caracteresPermitidosNombre(cadena);
       }
}
