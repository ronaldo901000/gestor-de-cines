package com.ronaldo.gestor.cines.api.rest.models.reporteGanancias;

import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.models.pagosBloqueo.PagoBloqueo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReporteGanancias {

       private List<CostoCine> costosCines;
       private List<AnuncioResponse> anuncios;
       private List<PagoBloqueo> pagosBloqueo;
       private BalanceFinanciero balanceFinanciero;

       public List<CostoCine> getCostosCines() {
              return costosCines;
       }

       public void setCostosCines(List<CostoCine> costosCines) {
              this.costosCines = costosCines;
       }

       public List<AnuncioResponse> getAnuncios() {
              return anuncios;
       }

       public void setAnuncios(List<AnuncioResponse> anuncios) {
              this.anuncios = anuncios;
       }

       public List<PagoBloqueo> getPagosBloqueo() {
              return pagosBloqueo;
       }

       public void setPagosBloqueo(List<PagoBloqueo> pagosBloqueo) {
              this.pagosBloqueo = pagosBloqueo;
       }

       public BalanceFinanciero getBalanceFinanciero() {
              return balanceFinanciero;
       }

       public void setBalanceFinanciero(BalanceFinanciero balanceFinanciero) {
              this.balanceFinanciero = balanceFinanciero;
       }

       public double calcularCostosTotal() {
              double total = 0;
              for (int i = 0; i < costosCines.size(); i++) {
                     total += costosCines.get(i).getCosto();
              }
              return total;
       }
       
       public double calcularIngresosTotales() {
              int totalIngresos = 0;
              for (int i = 0; i < anuncios.size(); i++) {
                     totalIngresos += anuncios.get(i).getCostoTotal();
              }

              for (int i = 0; i < pagosBloqueo.size(); i++) {
                     totalIngresos += pagosBloqueo.get(i).getCosto();
              }
              return totalIngresos;
       }
       
}
