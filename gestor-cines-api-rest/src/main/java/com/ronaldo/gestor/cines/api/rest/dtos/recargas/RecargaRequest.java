package com.ronaldo.gestor.cines.api.rest.dtos.recargas;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class RecargaRequest extends EntidadRequest {

       private String idUsuario;
       private double monto;

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public double getMonto() {
              return monto;
       }

       public void setMonto(double monto) {
              this.monto = monto;
       }

}
