package com.ronaldo.gestor.cines.api.rest.dtos.credenciales;

/**
 *
 * @author ronaldo
 */
public class CredencialRequest {

       private String correo;
       private String contraseña;

       public String getCorreo() {
              return correo;
       }

       public void setCorreo(String id) {
              this.correo = id;
       }

       public String getContraseña() {
              return contraseña;
       }

       public void setContraseña(String contraseña) {
              this.contraseña = contraseña;
       }

}
