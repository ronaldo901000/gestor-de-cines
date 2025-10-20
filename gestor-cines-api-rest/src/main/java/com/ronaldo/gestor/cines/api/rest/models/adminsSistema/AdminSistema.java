package com.ronaldo.gestor.cines.api.rest.models.adminsSistema;

import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class AdminSistema implements Editable{
       private String id;

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }
       
       public boolean datoValido(){
              return StringUtils.isNotBlank(id);
       }
}
