package com.ronaldo.gestor.cines.api.rest.models.anuncios;

import com.ronaldo.gestor.cines.api.rest.db.preciosAnunciosDB.PreciosAnunciosDB;
import com.ronaldo.gestor.cines.api.rest.enums.caracter.LimiteCaracter;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Anuncio {

       protected String codigo;
       protected String idAnunciante;
       protected String titulo;
       protected String tipo;
       protected String descripcion;
       protected LocalDate fechaRegistro;
       protected double precio;
       protected int duracion;
       
       protected int diasActivo;
       protected boolean activo;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getIdAnunciante() {
              return idAnunciante;
       }

       public void setIdAnunciante(String idAnunciante) {
              this.idAnunciante = idAnunciante;
       }

       public String getTitulo() {
              return titulo;
       }

       public void setTitulo(String titulo) {
              this.titulo = titulo;
       }

       public String getTipo() {
              return tipo;
       }

       public void setTipo(String tipo) {
              this.tipo = tipo;
       }

       public String getDescripcion() {
              return descripcion;
       }

       public void setDescripcion(String descripcion) {
              this.descripcion = descripcion;
       }

       public LocalDate getFechaRegistro() {
              return fechaRegistro;
       }

       public void setFechaRegistro(LocalDate fechaRegistro) {
              this.fechaRegistro = fechaRegistro;
       }

       public double getPrecio() {
              return precio;
       }

       public void setPrecio(double precio) {
              this.precio = precio;
       }

       public int getDuracion() {
              return duracion;
       }

       public void setDuracion(int duracion) {
              this.duracion = duracion;
       }

       public int getDiasActivo() {
              return diasActivo;
       }

       public void setDiasActivo(int diasActivo) {
              this.diasActivo = diasActivo;
       }

       public boolean isActivo() {
              return activo;
       }

       public void setActivo(boolean activo) {
              this.activo = activo;
       }

       public boolean datosValidos() {
              return StringUtils.isNotBlank(codigo)
                      && StringUtils.isNotBlank(idAnunciante)
                      && StringUtils.isNotBlank(titulo)
                      && StringUtils.isNotBlank(tipo)
                      && StringUtils.isNotBlank(descripcion)
                      && fechaRegistro != null
                      && precio >= 0
                      && caracteresValidos(codigo)
                      && limiteCaracteresValido(codigo, LimiteCaracter.CODIGO.get())
                      && limiteCaracteresValido(titulo, LimiteCaracter.TITULO_ANUNCIO.get())
                      && limiteCaracteresValido(descripcion, LimiteCaracter.DESCRIPCION_ANUNCIO.get())
                      && tipoValido();
       }

       private boolean caracteresValidos(String cadena) {
              VerificadorCaracteres verificador = new VerificadorCaracteres();

              return verificador.caracteresPermitidosNombre(cadena);
       }

       private boolean limiteCaracteresValido(String cadena, int maximo) {
              VerificadorCaracteres verificador = new VerificadorCaracteres();
              return verificador.numeroCaracteresValido(cadena, maximo);
       }

       private boolean tipoValido() {
              return tipo.equals(TipoAnuncio.TEXTO.name()) || tipo.equals(TipoAnuncio.IMAGEN.name())
                      | tipo.equals(TipoAnuncio.VIDEO.name());
       }
       
       
       public double calcularCantidadApagar(int id) throws DataBaseException{
              PreciosAnunciosDB preciosAnunciosDB= new PreciosAnunciosDB();
              double costo=preciosAnunciosDB.obtenerPrecio(id);
              return costo*duracion;
       }
}
