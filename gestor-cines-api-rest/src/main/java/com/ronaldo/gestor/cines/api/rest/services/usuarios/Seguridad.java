package com.ronaldo.gestor.cines.api.rest.services.usuarios;

import com.ronaldo.gestor.cines.api.rest.exceptions.CredencialInvalidadException;
import com.ronaldo.gestor.cines.api.rest.models.credenciales.Credencial;
import java.util.Base64;

/**
 *
 * @author ronaldo
 */
public class Seguridad {

       public void verificarCredenciales(Credencial ingresada, Credencial correcta) throws CredencialInvalidadException {

              ingresada.setContraseña(encriptarContraseña(ingresada.getContraseña()));

              if (!ingresada.getContraseña().equals(correcta.getContraseña())) {
                     throw new CredencialInvalidadException("Contraseña incorrecta");
              }
       }

       public String encriptarContraseña(String contraseña) {
              return Base64.getEncoder().encodeToString(contraseña.getBytes());
       }

       public String decodificarContraseña(String cadena) {
              byte[] decodificado = Base64.getDecoder().decode(cadena);
              return new String(decodificado);
       }
}
