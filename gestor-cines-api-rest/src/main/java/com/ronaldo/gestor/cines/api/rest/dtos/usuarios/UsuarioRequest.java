package com.ronaldo.gestor.cines.api.rest.dtos.usuarios;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class UsuarioRequest extends EntidadRequest {

       private String id;
       private String nombre;
       private String correo;
       private String contraseña;
       private String telefono;

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }

       public String getCorreo() {
              return correo;
       }

       public void setCorreo(String correo) {
              this.correo = correo;
       }

       public String getContraseña() {
              return contraseña;
       }

       public void setContraseña(String contraseña) {
              this.contraseña = contraseña;
       }

       public String getTelefono() {
              return telefono;
       }

       public void setTelefono(String telefono) {
              this.telefono = telefono;
       }

}
