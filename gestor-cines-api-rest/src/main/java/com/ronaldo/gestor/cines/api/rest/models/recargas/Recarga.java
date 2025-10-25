package com.ronaldo.gestor.cines.api.rest.models.recargas;

import com.ronaldo.gestor.cines.api.rest.enums.caracter.RangoMonto;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Recarga implements Editable {

       private String llavePrimaria;
       private double monto;

       public String getLlavePrimaria() {
              return llavePrimaria;
       }

       public void setLlavePrimaria(String idUsuario) {
              this.llavePrimaria = idUsuario;
       }

       public double getMonto() {
              return monto;
       }

       public void setMonto(double monto) {
              this.monto = monto;
       }

       public boolean datosValidos() {
              return StringUtils.isNotBlank(llavePrimaria)
                      && monto >= RangoMonto.RECARGA.getMinimo()
                      && monto <= RangoMonto.RECARGA.getMaximo();

       }

}
