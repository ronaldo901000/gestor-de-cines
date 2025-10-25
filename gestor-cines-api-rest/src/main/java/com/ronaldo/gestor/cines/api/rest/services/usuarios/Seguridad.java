package com.ronaldo.gestor.cines.api.rest.services.usuarios;

import java.util.Base64;

/**
 *
 * @author ronaldo
 */
public class Seguridad {

       public String encriptarContraseña(String contraseña) {
              return Base64.getEncoder().encodeToString(contraseña.getBytes());
       }

       public String decodificarContraseña(String cadena) {
              byte[] decodificado = Base64.getDecoder().decode(cadena);
              return new String(decodificado);
       }
}
