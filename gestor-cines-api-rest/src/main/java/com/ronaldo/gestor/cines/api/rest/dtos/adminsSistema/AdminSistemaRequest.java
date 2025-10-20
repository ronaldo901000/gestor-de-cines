package com.ronaldo.gestor.cines.api.rest.dtos.adminsSistema;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class AdminSistemaRequest extends EntidadRequest{
       private String id;

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }
       
       
}
