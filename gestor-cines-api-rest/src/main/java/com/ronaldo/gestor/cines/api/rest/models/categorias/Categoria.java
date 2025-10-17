package com.ronaldo.gestor.cines.api.rest.models.categorias;

import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Categoria implements Editable {

       private int id;
       private String nombre;

       public Categoria() {
       }

       public Categoria(int id, String nombre) {
              this.id = id;
              this.nombre = nombre;
       }
       
       public String getNombre() {
              return nombre;
       }

       public int getId() {
              return id;
       }

       public void setId(int id) {
              this.id = id;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }

       public boolean datoValido() {
              if (StringUtils.isNotBlank(nombre)) {
                     this.nombre = this.nombre.toUpperCase().trim();
                     return true;
              }
              return false;
       }

}
