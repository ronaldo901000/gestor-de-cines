package com.ronaldo.gestor.cines.api.rest.dtos.anuncios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class AnuncioResponse {

       private String codigo;
       private String idAnunciante;
       private String titulo;
       private String tipo;
       private String descripcion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonSerialize(using = LocalDateSerializer.class)
       private LocalDate fechaRegistro;
       private double precio;
       private int diasDuracion;
       private int diasActivo;
       private boolean activo;

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

       public int getDiasDuracion() {
              return diasDuracion;
       }

       public void setDiasDuracion(int diasDuracion) {
              this.diasDuracion = diasDuracion;
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

}
