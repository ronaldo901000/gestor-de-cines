package com.ronaldo.gestor.cines.api.rest.models.credenciales;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Credencial {

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
       
       public boolean datosValidos() {
              return StringUtils.isNotBlank(correo)
                      && StringUtils.isNotBlank(contraseña);
              
       }
}
