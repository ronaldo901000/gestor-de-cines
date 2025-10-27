package com.ronaldo.gestor.cines.api.rest.models.salas;

import com.ronaldo.gestor.cines.api.rest.enums.caracter.LimiteCaracter;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Sala implements Editable {

       private static final int MINIMO = 1;
       private static final int MAXIMO = 25;
       private String codigo;
       private String nombre;
       private String codigoCine;
       private String nombreCine;
       private int filas;
       private int columnas;
       private boolean comentariosYCalificacionesHabilitados;
       private boolean activo;

       public Sala() {
       }
       
       public int obtenerCapacidad(){
              return filas*columnas;
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

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public String getNombreCine() {
              return nombreCine;
       }

       public void setNombreCine(String nombreCine) {
              this.nombreCine = nombreCine;
       }

       public int getFilas() {
              return filas;
       }

       public void setFilas(int filas) {
              this.filas = filas;
       }

       public int getColumnas() {
              return columnas;
       }

       public void setColumnas(int columnas) {
              this.columnas = columnas;
       }

       public boolean isComentariosYCalificacionesHabilitados() {
              return comentariosYCalificacionesHabilitados;
       }

       public void setComentariosYCalificacionesHabilitados(boolean comentariosYCalificacionesHabilitados) {
              this.comentariosYCalificacionesHabilitados = comentariosYCalificacionesHabilitados;
       }

       public boolean isActivo() {
              return activo;
       }

       public void setActivo(boolean activo) {
              this.activo = activo;
       }

       public boolean datosCorrectos() {
              return StringUtils.isNotBlank(codigo)
                      && StringUtils.isNotBlank(nombre)
                      && StringUtils.isNotBlank(codigoCine)
                      && cantidadValida(filas)
                      && cantidadValida(columnas)
                      && caracteresValidos(codigo, LimiteCaracter.CODIGO.get())
                      && caracteresValidos(nombre, LimiteCaracter.NOMBRE.get());

       }

       private boolean cantidadValida(int valor) {
              return valor >= MINIMO && valor <= MAXIMO;
       }

       private boolean caracteresValidos(String cadena, int limiteCaracteres) {
              VerificadorCaracteres verificador = new VerificadorCaracteres();

              return verificador.caracteresPermitidosNombre(cadena) && verificador.numeroCaracteresValido(cadena, limiteCaracteres);
       }

}
