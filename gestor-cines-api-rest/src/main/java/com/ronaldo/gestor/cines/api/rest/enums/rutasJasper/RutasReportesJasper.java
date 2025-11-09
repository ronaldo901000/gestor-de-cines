package com.ronaldo.gestor.cines.api.rest.enums.rutasJasper;

/**
 *
 * @author ronaldo
 */
public enum RutasReportesJasper {
       REPORTE_COMENTARIOS_DE_SALAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminCine/ReporteComentariosSalas.jasper"),
       REPORTE_PELICULAS_PROYECTADAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminCine/ReportePeliculasProyectadas.jasper"),
       REPORTE_TOP_SALAS_GUSTADAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminCine/ReporteSalasGustadas.jasper"),
       REPORTE_BOLETOS_VENDIDOS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminCine/ReporteBoletosVendidos.jasper"),
       REPORTE_GANANCIAS_ANUNCIANTES("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminSistema/ReporteGananciasAnunciates.jasper"),
       REPORTE_SALAS_POPULARES("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminSistema/ReporteSalasPopulares.jasper"),
       REPORTE_SALAS_COMENTADAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminSistema/ReporteSalasComentadas.jasper"),
       REPORTE_GANANCIAS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminSistema/ReporteGanancias.jasper"),
       REPORTE_ANUNCIOS_COMPRADOS("com/ronaldo/gestor/cines/api/rest/reportes/reportesAdminSistema/ReporteAnunciosComprados.jasper"),
       
       ;
       
       
       private String ruta;

       private RutasReportesJasper(String ruta) {
              this.ruta = ruta;
       }

       public String getRuta() {
              return ruta;
       }
       
       
}
