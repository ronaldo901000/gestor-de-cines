package com.ronaldo.gestor.cines.api.rest.models.adminCine;

import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;

/**
 *
 * @author ronaldo
 */
public class AdminCine implements Editable{

       private String codigoCine;
       private String id;

       public AdminCine(String codigoCine, String id) {
              this.codigoCine = codigoCine;
              this.id = id;
       }

       public AdminCine() {
       }

       
       
       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigo) {
              this.codigoCine = codigo;
       }

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }

}
