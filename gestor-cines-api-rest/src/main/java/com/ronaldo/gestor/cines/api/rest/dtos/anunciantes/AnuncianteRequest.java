package com.ronaldo.gestor.cines.api.rest.dtos.anunciantes;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class AnuncianteRequest extends EntidadRequest{

       private String idUsuario;

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

}
