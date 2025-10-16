package com.ronaldo.gestor.cines.api.rest.dtos.adminCine;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class AdminCineRequest extends EntidadRequest {

       private String id;
       private String codigoCine;

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

}
