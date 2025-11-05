package com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine;

import com.ctc.wstx.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class FiltroReporteRequest {

       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       protected LocalDate fechaInicio;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       protected LocalDate fechaFin;

       public LocalDate getFechaInicio() {
              return fechaInicio;
       }

       public void setFechaInicio(String fechaInicio) {
              if (StringUtils.isNotBlank(fechaInicio)) {
                     this.fechaInicio = LocalDate.parse(fechaInicio.substring(0, 10));
              }
       }

       public LocalDate getFechaFin() {

              return fechaFin;
       }

       public void setFechaFin(String fechaFin) {
              if (StringUtils.isNotBlank(fechaFin)) {
                     this.fechaFin = LocalDate.parse(fechaFin.substring(0, 10));
              }
       }

}
