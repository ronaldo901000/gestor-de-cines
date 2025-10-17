package com.ronaldo.gestor.cines.api.rest.models.preciosAnuncios;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class PrecioAnuncio {
       private static final int VALOR_MAXIMO=999999999;
       private int id;
       private String tipo;
       private double precioVentaPorDia;
       private double precioBloqueoPorDia;

       public PrecioAnuncio(int id, String tipo, double precioVentaPorDia, double precioBloqueoPorDia) {
              this.id = id;
              this.tipo = tipo;
              this.precioVentaPorDia = precioVentaPorDia;
              this.precioBloqueoPorDia = precioBloqueoPorDia;
       }

       public PrecioAnuncio() {
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
       
       public boolean validoEnCampos() {
              return StringUtils.isNotBlank(String.valueOf(id))
                      && StringUtils.isNotBlank(String.valueOf(precioVentaPorDia))
                      && StringUtils.isNotBlank(String.valueOf(precioVentaPorDia))
                      && cantidadDigitosValidos(precioVentaPorDia)
                      && cantidadDigitosValidos(precioBloqueoPorDia);
       }
       
       /**
        * 
        * @param valor
        * @return 
        */
       private boolean cantidadDigitosValidos(double valor) {
              try {
                     return valor >= 0 && valor <= VALOR_MAXIMO;
              } catch (NumberFormatException e) {
                     return false;
              }

       }

}
