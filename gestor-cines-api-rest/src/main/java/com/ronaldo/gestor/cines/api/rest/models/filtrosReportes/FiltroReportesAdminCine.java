package com.ronaldo.gestor.cines.api.rest.models.filtrosReportes;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class FiltroReportesAdminCine extends FiltroReporte {
       public static final int FILTRO_COMPLETO=0;
       public static final int FILTRO_NULO=1;
       public static final int FILTRO_SALA=2;
       public static final int FILTRO_FECHAS=3;
       
       private String codigoCine;
       private String codigoSala;
       private int tipoFiltro;
       private String query;

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public String getCodigoSala() {
              return codigoSala;
       }

       public void setCodigoSala(String codigoSala) {
              this.codigoSala = codigoSala;
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

       @Override
       public LocalDate getFechaInicio() {
              return fechaInicio;
       }

       @Override
       public void setFechaInicio(LocalDate fechaInicio) {
              this.fechaInicio = fechaInicio;
       }

       @Override
       public LocalDate getFechaFin() {
              return fechaFin;
       }

       @Override
       public void setFechaFin(LocalDate fechaFin) {
              this.fechaFin = fechaFin;
       }

       public void generarQuery(List<String> querys) throws UserDataInvalidException {

              if (fechaInicio != null && fechaFin != null && StringUtils.isNotBlank(codigoSala)) {
                     query=querys.get(FILTRO_COMPLETO);
                     tipoFiltro = FILTRO_COMPLETO;
              } else if (fechaInicio == null && StringUtils.isBlank(codigoSala)) {
                     query=querys.get(FILTRO_NULO);
                     tipoFiltro = FILTRO_NULO;
              } else if (fechaInicio == null && StringUtils.isNotBlank(codigoSala)) {
                     query=querys.get(FILTRO_SALA);
                     tipoFiltro = FILTRO_SALA;
              } else if (fechaInicio != null && fechaFin != null && StringUtils.isBlank(codigoSala)) { 
                     query=querys.get(FILTRO_FECHAS);
                     tipoFiltro = FILTRO_FECHAS;
              } else {
                     throw new UserDataInvalidException("Los parametros no coinciden con ningun filtro valido");
              }
              
       }

       public void datoaValidos() throws UserDataInvalidException {
              if (StringUtils.isBlank(codigoCine)) {
                     throw new UserDataInvalidException("Error, codigo de cine no enviado");
              }
              verificarFechas();
       }

       private void verificarFechas() throws UserDataInvalidException {
              if (fechaInicio != null && fechaFin != null) {
                     if (fechaInicio.isAfter(fechaFin)) {
                            throw new UserDataInvalidException("La fecha inicial no puede ser posterior a la fecha final");
                     }
              } else if ((fechaInicio == null && fechaFin != null) || (fechaInicio != null && fechaFin == null)) {
                     throw new UserDataInvalidException("Debe enviar ambas fechas, inicio y fin o ninguna");
              }
       }

       public void existeCineYSala() throws DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();

              if (!herramientaDB.existeEntidad(codigoCine, PeticionAdminSistema.OBTENER_CINE_POR_CODIGO.get())) {
                     throw new EntityNotFoundException("Cine no encontrado en la base de datos");
              }

              if (StringUtils.isNotBlank(codigoSala)) {
                     if (!herramientaDB.existeEntidad(codigoSala, PeticionesAdminCine.BUSCAR_SALA.get())) {
                            System.out.println("sala no encontrada");
                            throw new EntityNotFoundException("Sala no encontrada en la base de datos");
                     }
              }
       }


}
