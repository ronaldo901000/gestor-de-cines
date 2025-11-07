package com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos;

import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReporteBoletosVendidos {

       private Cine cine;
       private List<CompraBoletosSala> comprasPorSalas;

       public Cine getCine() {
              return cine;
       }

       public void setCine(Cine cine) {
              this.cine = cine;
       }

       public List<CompraBoletosSala> getComprasPorSalas() {
              return comprasPorSalas;
       }

       public void setComprasPorSalas(List<CompraBoletosSala> comprasPorSalas) {
              this.comprasPorSalas = comprasPorSalas;
       }

}
