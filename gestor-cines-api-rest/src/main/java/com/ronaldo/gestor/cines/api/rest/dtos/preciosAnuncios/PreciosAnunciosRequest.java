package com.ronaldo.gestor.cines.api.rest.dtos.preciosAnuncios;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class PreciosAnunciosRequest extends EntidadRequest {

       private int id;
       private String tipo;
       private double precioVentaPorDia;
       private double precioBloqueoPorDia;
       

       public int getId() {
              return id;
       }

       public void setId(int id) {
              this.id = id;
       }

       public double getPrecioVentaPorDia() {
              return precioVentaPorDia;
       }

       public void setPrecioVentaPorDia(double precioVentaPorDia) {
              this.precioVentaPorDia = precioVentaPorDia;
       }

       public double getPrecioBloqueoPorDia() {
              return precioBloqueoPorDia;
       }

       public void setPrecioBloqueoPorDia(double precioBloqueoPorDia) {
              this.precioBloqueoPorDia = precioBloqueoPorDia;
       }

       public String getTipo() {
              return tipo;
       }

       public void setTipo(String tipo) {
              this.tipo = tipo;
       }
       
       
}
