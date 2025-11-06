package com.ronaldo.gestor.cines.api.rest.enums.rutasJasper;

/**
 *
 * @author ronaldo
 */
public enum RutasReportesJasper {
       REPORTE_COMENTARIOS_DE_SALAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminCine/ReporteComentariosSalas.jasper"),
       REPORTE_PELICULAS_PROYECTADAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminCine/ReportePeliculasProyectadas.jasper"),;
       private String ruta;

       private RutasReportesJasper(String ruta) {
              this.ruta = ruta;
       }

       public String getRuta() {
              return ruta;
       }
       
       
}
