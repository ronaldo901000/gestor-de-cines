package com.ronaldo.gestor.cines.api.rest.dtos.pagoBloqueoAnuncio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class PagoBloqueoRequest {

       private String codigoCine;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fechaPago;
       private int totalDias;

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public LocalDate getFechaPago() {
              return fechaPago;
       }

       public void setFechaPago(LocalDate fechaPago) {
              this.fechaPago = fechaPago;
       }

       public int getTotalDias() {
              return totalDias;
       }

       public void setTotalDias(int totalDias) {
              this.totalDias = totalDias;
       }

}
