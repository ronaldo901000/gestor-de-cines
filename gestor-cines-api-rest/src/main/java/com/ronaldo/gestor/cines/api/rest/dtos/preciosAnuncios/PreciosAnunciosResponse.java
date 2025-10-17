package com.ronaldo.gestor.cines.api.rest.dtos.preciosAnuncios;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.preciosAnuncios.PrecioAnuncio;

/**
 *
 * @author ronaldo
 */
public class PreciosAnunciosResponse extends EntidadResponse {

       private int id;
       private String tipo;
       private double precioVentaPorDia;
       private double precioBloqueoPorDia;

       public PreciosAnunciosResponse(PrecioAnuncio precioAnuncio) {
              this.id = precioAnuncio.getId();
              this.tipo = precioAnuncio.getTipo();
              this.precioVentaPorDia = precioAnuncio.getPrecioVentaPorDia();
              this.precioBloqueoPorDia = precioAnuncio.getPrecioBloqueoPorDia();
       }

       public int getId() {
              return id;
       }

       public void setId(int id) {
              this.id = id;
       }

       public String getTipo() {
              return tipo;
       }

       public void setTipo(String tipo) {
              this.tipo = tipo;
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
}
