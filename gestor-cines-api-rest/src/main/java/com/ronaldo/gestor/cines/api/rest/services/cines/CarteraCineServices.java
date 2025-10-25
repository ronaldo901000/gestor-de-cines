/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.services.cines;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.dtos.recargas.RecargaCineRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.recargas.Recarga;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class CarteraCineServices {

       /**
        * 
        * @param codigoCine
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       public double obtenerSaldoActual(String codigoCine) throws UserDataInvalidException, DataBaseException, EntityNotFoundException {
              CinesDB cinesDB = new CinesDB();
              if (!StringUtils.isNotBlank(codigoCine)) {
                     throw new UserDataInvalidException("Error en el codigo de cine enviado");
              }
              Optional<Double> saldo = cinesDB.obtenerSaldoActual(codigoCine);
              if (saldo.isEmpty()) {
                     throw new EntityNotFoundException("No se encontro el cine indicado");
              }
              return saldo.get();
       }

       /**
        * 
        * @param request
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       public double recargarCartera(RecargaCineRequest request) throws UserDataInvalidException, DataBaseException, EntityNotFoundException {
              Recarga recarga = extraer(request);
              CinesDB cinesDB = new CinesDB();
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(recarga.getLlavePrimaria());
              if (cine.isEmpty()) {
                     throw new EntityNotFoundException("Cine no registrado en la base de datos");
              }
              cinesDB.recargar(recarga);

              return obtenerSaldoActual(cine.get().getCodigo());
       }

       /**
        * 
        * @param request
        * @return
        * @throws UserDataInvalidException 
        */
       private Recarga extraer(RecargaCineRequest request) throws UserDataInvalidException {
              Recarga recarga = new Recarga();
              recarga.setLlavePrimaria(request.getCodigoCine());
              recarga.setMonto(request.getMonto());

              if (!recarga.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return recarga;
       }
}
