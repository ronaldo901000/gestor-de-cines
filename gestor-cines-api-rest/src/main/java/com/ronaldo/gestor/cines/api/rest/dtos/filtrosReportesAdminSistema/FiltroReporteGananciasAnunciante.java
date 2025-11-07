package com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroReporteRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class FiltroReporteGananciasAnunciante extends FiltroReporteRequest {

       public static final int FILTRO_COMPLETO = 0;
       public static final int FILTRO_ANUNCIANTE = 1;
       public static final int FILTRO_FECHAS = 2;
       public static final int FILTRO_NULO = 3;

       private String idAnunciante;
       private String query;
       private int tipoFiltro;

       public String getIdAnunciante() {
              return idAnunciante;
       }

       public void setIdAnunciante(String idAnunciante) {
              this.idAnunciante = idAnunciante;
       }

       @Override
       public LocalDate getFechaInicio() {
              return fechaInicio;
       }

       public void setFechaInicio(LocalDate fechaInicio) {
              this.fechaInicio = fechaInicio;
       }

       @Override
       public LocalDate getFechaFin() {
              return fechaFin;
       }

       public void setFechaFin(LocalDate fechaFin) {
              this.fechaFin = fechaFin;
       }

       public String getQuery() {
              return query;
       }

       public void setQuery(String query) {
              this.query = query;
       }

       public int getTipoFiltro() {
              return tipoFiltro;
       }

       public void setTipoFiltro(int tipoFiltro) {
              this.tipoFiltro = tipoFiltro;
       }

       public boolean esAnunciante() throws DataBaseException {

              HerramientaDB herramientaDB = new HerramientaDB();

              if (StringUtils.isNotBlank(idAnunciante)) {
                     return herramientaDB.existeEntidad(idAnunciante, PeticionAdminSistema.BUSCAR_ANUNCIANTE.get());
              }
              return true;
       }

       public void generarQuery() {
              if (StringUtils.isNotBlank(idAnunciante) && fechaInicio != fechaInicio && fechaFin != null) {
                     tipoFiltro = FILTRO_COMPLETO;
              } else if (StringUtils.isNotBlank(idAnunciante) && fechaInicio == null && fechaFin == null) {
                     tipoFiltro = FILTRO_ANUNCIANTE;
              } else if (StringUtils.isBlank(idAnunciante) && fechaInicio != null && fechaFin != null) {
                     tipoFiltro = FILTRO_FECHAS;
              } else {
                     tipoFiltro = FILTRO_NULO;
              }
       }

}
