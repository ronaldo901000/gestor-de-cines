package com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes;

import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReporteGananciaAnunciantes {

       List<AnuncianteReport> anuncianteaReport;

       public List<AnuncianteReport> getAnuncianteaReport() {
              return anuncianteaReport;
       }

       public void setAnuncianteaReport(List<AnuncianteReport> anuncianteaReport) {
              this.anuncianteaReport = anuncianteaReport;
       }

}
