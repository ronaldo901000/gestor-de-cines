package com.ronaldo.gestor.cines.api.rest.services.costosFuncionamiento;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.costosFuncionamiento.CostosFuncionamientoDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.costosFuncionamiento.CostoFuncionamientoCineRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.costosFuncionamiento.CostoFuncionamientoCineResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.costosFuncionamiento.CostoFuncionamiento;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import com.ronaldo.gestor.cines.api.rest.verificacion.caracter.VerificadorCaracteres;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CRUDCostosFuncionamiento extends CRUD {

       /**
        * 
        * @param entidadRequest
        * @return
        * @throws UserDataInvalidException
        * @throws EntityAlreadyExistsException
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       @Override
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              CostosFuncionamientoDB db = new CostosFuncionamientoDB();
              CostoFuncionamiento costo = (CostoFuncionamiento) extraer(entidadRequest);

              if (!herramientaDB.existeEntidad(costo.getCodigoCine(), PeticionAdminSistema.BUSCAR_CINE.get())) {
                     throw new EntityNotFoundException("El codigo '" + costo.getCodigoCine() + "' no pertenece a ningun cine, verifica");
              }
              // si ya existe registro de costo en la fecha indicada, se sobreescribe
              if (db.existeRegistroCostoEnFecha(costo)) {
                     db.sobreEscribir(costo);
              } //si no exite se crea
              else {
                     db.crearRegistro(costo);
              }
              return costo;

       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              CostoFuncionamientoCineRequest costoRequest = (CostoFuncionamientoCineRequest) entidadRequest;
              CostoFuncionamiento costo = new CostoFuncionamiento();

              costo.setCodigoCine(costoRequest.getCodigoCine());
              costo.setFechaRegistro(costoRequest.getFechaRegistro());
              costo.setCosto(costoRequest.getCosto());

              if (!costo.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados, verifica por favor");
              }
              return costo;
       }

       public List<CostoFuncionamientoCineResponse> obtenerCinesPorNombreOCodigo(String palabra) throws DataBaseException, UserDataInvalidException {
             if(palabra==null){
                    throw new UserDataInvalidException("Por favor ingresa una palabra");
             }
              List<CostoFuncionamientoCineResponse> costosResponse = new ArrayList<>();
              VerificadorCaracteres verificadorCaracteres = new VerificadorCaracteres();
              
              if (!verificadorCaracteres.caracteresPermitidosNombre(palabra)) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              CostosFuncionamientoDB costosDB = new CostosFuncionamientoDB();

              List<CostoFuncionamiento> costos = costosDB.obtenerCostosPorNombreOCodigo(palabra);
              crearResponse(costos, costosResponse);
              return costosResponse;
       }

       private void crearResponse(
               List<CostoFuncionamiento> costos,
               List<CostoFuncionamientoCineResponse> costosResponse) throws DataBaseException {
              
              CinesDB cinesDB = new CinesDB();
              if(costos.isEmpty()){
                     return;
              }
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(costos.get(0).getCodigoCine());
              if (cine.isEmpty()) {
                     return;
              }
              for (int i = 0; i < costos.size(); i++) {
                     costosResponse.add(new CostoFuncionamientoCineResponse(costos.get(i)));
                     costosResponse.get(i).setNombreCine(cine.get().getNombre());
              }
              
       }
       
       @Override
       protected EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public void eliminar(String id) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              CostosFuncionamientoDB costosDB = new CostosFuncionamientoDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              if (!herramientaDB.existeEntidad(id, PeticionAdminSistema.BUSCAR_COSTO_POR_ID.get())) {
                     throw new EntityNotFoundException("No se encontro el costo con id " + id + " en el sistema");
              }
              costosDB.eliminar(id);
       }
}
