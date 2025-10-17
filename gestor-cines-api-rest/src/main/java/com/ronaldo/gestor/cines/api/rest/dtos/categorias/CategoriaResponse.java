package com.ronaldo.gestor.cines.api.rest.dtos.categorias;

import com.ronaldo.gestor.cines.api.rest.models.categorias.Categoria;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;

/**
 *
 * @author ronaldo
 */
public class CategoriaResponse extends EntidadResponse {

       private int id;
       private String nombre;

       public CategoriaResponse(Categoria categoria) {
              this.id=categoria.getId();
              this.nombre=categoria.getNombre();
       }

       public int getId() {
              return id;
       }

       public void setId(int id) {
              this.id = id;
       }

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }

}
