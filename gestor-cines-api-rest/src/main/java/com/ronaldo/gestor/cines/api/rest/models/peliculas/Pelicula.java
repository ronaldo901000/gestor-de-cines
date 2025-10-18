package com.ronaldo.gestor.cines.api.rest.models.peliculas;

import com.ronaldo.gestor.cines.api.rest.enums.caracter.LimiteCaracter;
import com.ronaldo.gestor.cines.api.rest.enums.clasificaciones.Clasificaciones;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Pelicula implements Editable {

       private String codigo;
       private String titulo;
       private String sinopsis;
       private int duracion;
       private String director;
       private String cast;
       private String clasificacion;
       private LocalDate fechaEstreno;
       private List<String> idsCategorias;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getTitulo() {
              return titulo;
       }

       public void setTitulo(String titulo) {
              this.titulo = titulo;
       }

       public String getSinopsis() {
              return sinopsis;
       }

       public void setSinopsis(String sinopsis) {
              this.sinopsis = sinopsis;
       }

       public int getDuracion() {
              return duracion;
       }

       public void setDuracion(int duracion) {
              this.duracion = duracion;
       }

       public String getDirector() {
              return director;
       }

       public void setDirector(String director) {
              this.director = director;
       }

       public String getCast() {
              return cast;
       }

       public void setCast(String cast) {
              this.cast = cast;
       }

       public String getClasificacion() {
              return clasificacion;
       }

       public void setClasificacion(String clasificacion) {
              this.clasificacion = clasificacion;
       }

       public LocalDate getFechaEstreno() {
              return fechaEstreno;
       }

       public void setFechaEstreno(LocalDate fechaEstreno) {
              this.fechaEstreno = fechaEstreno;
       }

       public List<String> getIdsCategorias() {
              return idsCategorias;
       }

       public void setIdsCategorias(List<String> idsCategorias) {
              this.idsCategorias = idsCategorias;
       }

       

       public boolean datosValidos() {
              return StringUtils.isNotBlank(codigo)
                      && StringUtils.isNotBlank(titulo)
                      && StringUtils.isNotBlank(sinopsis)
                      && StringUtils.isNotBlank(director)
                      && StringUtils.isNotBlank(cast)
                      && StringUtils.isNotBlank(clasificacion)
                      && idsCategorias != null
                      && fechaEstreno!=null
                      && duracionValida()
                      && codigoTituloCorrecto()
                      && limiteCaracteresCorrecto()
                      && tieneClasificacionValida();
       }

       private boolean duracionValida() {
              return duracion > 0;
       }

       private boolean codigoTituloCorrecto() {
              VerificadorCaracteres verificador = new VerificadorCaracteres();
              return verificador.caracteresPermitidosNombre(codigo)
                      && verificador.caracteresPermitidosNombre(titulo);
       }

       private boolean limiteCaracteresCorrecto() {
              VerificadorCaracteres verificador = new VerificadorCaracteres();
              return verificador.numeroCaracteresValido(codigo, LimiteCaracter.CODIGO.get())
                      && verificador.numeroCaracteresValido(titulo, LimiteCaracter.TITULO_PELICULA.get())
                      && verificador.numeroCaracteresValido(sinopsis, LimiteCaracter.SINOPSIS.get())
                      && verificador.numeroCaracteresValido(director, LimiteCaracter.DIRECTOR.get())
                      && verificador.numeroCaracteresValido(cast, LimiteCaracter.CAST.get());
       }

       private boolean tieneClasificacionValida() {
              return clasificacion.equals(Clasificaciones.A.get())
                      || clasificacion.equals(Clasificaciones.B.get())
                      || clasificacion.equals(Clasificaciones.B_12.get())
                      || clasificacion.equals(Clasificaciones.B_15.get())
                      || clasificacion.equals(Clasificaciones.C.get());
       }
}
