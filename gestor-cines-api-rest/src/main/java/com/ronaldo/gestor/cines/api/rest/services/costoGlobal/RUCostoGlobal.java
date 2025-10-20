package com.ronaldo.gestor.cines.api.rest.services.costoGlobal;

import com.ronaldo.gestor.cines.api.rest.db.costosFuncionamiento.CostosFuncionamientoDB;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.costoGlobal.CostoGlobal;

/**
 *
 * @author ronaldo
 */
public class RUCostoGlobal {
       private static final double MAXIMO=100000000;
       private static final double MINIMO=0;
       
       public CostoGlobal obtenerCostoGlobal() throws DataBaseException {
              CostosFuncionamientoDB costoFuncionamientoDb = new CostosFuncionamientoDB();
              return costoFuncionamientoDb.obtenerCostoGlobal();
       }

       public void actualizarCosto(double costo) throws DataBaseException, UserDataInvalidException {
              CostosFuncionamientoDB costoFuncionamientoDb = new CostosFuncionamientoDB();
              if(costo<MINIMO || costo>MAXIMO ){
                     throw new UserDataInvalidException("Ingresa valores entre '"+ MINIMO+"' y '"+MAXIMO+"'");
              }
              costoFuncionamientoDb.actualizarCostoGlobal(costo);
              
       }
}
