package com.ronaldo.gestor.cines.api.rest.dtos.categorias;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class CategoriaRequest extends EntidadRequest{
       private String nombre;

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }
       
       
       
}
