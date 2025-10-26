package com.ronaldo.gestor.cines.api.rest.dtos.usuarios;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;

/**
 *
 * @author ronaldo
 */
public class UsuarioResponse extends EntidadResponse {

       private String id;
       private String nombre;
       private String correo;
       private String telefono;

       public UsuarioResponse(Usuario usuario) {
              this.id = usuario.getId();
              this.nombre = usuario.getNombre();
              this.correo = usuario.getCorreo();
              this.telefono = usuario.getTelefono();
       }

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

       public String getTelefono() {
              return telefono;
       }

       public void setTelefono(String telefono) {
              this.telefono = telefono;
       }

}
