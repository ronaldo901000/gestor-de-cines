package com.ronaldo.gestor.cines.api.rest.services.salas;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.salas.SalasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.salas.SalaRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.salas.SalaResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.enums.query.RangoBusquedaElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CRUDSalas extends CRUD {

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
              SalasDB salasDB = new SalasDB();
              Sala sala = (Sala) extraer(entidadRequest);

              if (herramientaDB.existeEntidad(sala.getCodigo(), PeticionesAdminCine.BUSCAR_SALA.get())) {
                     throw new EntityAlreadyExistsException("Codigo de sala no disponible, utiliza otro");
              }
              if (!herramientaDB.existeEntidad(sala.getCodigoCine(), PeticionAdminSistema.BUSCAR_CINE.get())) {
                     throw new EntityNotFoundException("El codigo de cine ingresado no pertenece a ningun cine, verifica");
              }
              salasDB.crear(sala);
              return sala;
       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              SalaRequest request = (SalaRequest) entidadRequest;
              Sala sala = new Sala();
              sala.setCodigo(request.getCodigo());
              sala.setNombre(request.getNombre());
              sala.setCodigoCine(request.getCodigoCine());
              sala.setFilas(request.getFilas());
              sala.setColumnas(request.getColumnas());
              if (!sala.datosCorrectos()) {
                     throw new UserDataInvalidException("Error en los datos enviados, por favor verifica");
              }
              return sala;
       }

       /**
        * 
        * @param codigoCine
        * @param inicio
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException 
        */
       public List<SalaResponse> obtenerSalasRango(String codigoCine, int inicio) throws UserDataInvalidException, DataBaseException {
             
              SalasDB salasDB = new SalasDB();
              List<SalaResponse> salasResponse = new ArrayList<>();
              
              int fin=inicio+RangoBusquedaElemento.SALAS.getRango();
              
              List<Sala> salas = salasDB.obtenerSalas(codigoCine, inicio, fin);
              
              for (int i = 0; i < salas.size(); i++) {
                     salasResponse.add(new SalaResponse(salas.get(i)));
              }
              return salasResponse;
       }

       public SalaResponse obtenerSala(String codigo) throws EntityNotFoundException, DataBaseException {
              SalasDB salasDB = new SalasDB();
              Optional<Sala> sala = salasDB.obtenerSala(codigo);
              if (sala.isEmpty()) {
                     throw new EntityNotFoundException("El codigo " + codigo + " no pertenece a ninguna sala");
              }
              return new SalaResponse(sala.get());
       }

       /**
        *
        * @param entidadRequest
        * @return
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws UserDataInvalidException
        */
       @Override
       public EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException,
               EntityNotFoundException, UserDataInvalidException {
              SalasDB salasDB = new SalasDB();
              HerramientaDB herramientaDB = new HerramientaDB();

              Sala salaUpdate = (Sala) extraer(entidadRequest);

              if (!herramientaDB.existeEntidad(salaUpdate.getCodigo(), PeticionesAdminCine.BUSCAR_SALA.get())) {
                     throw new EntityNotFoundException("El codigo ingresado no pertenece a ninguna sala, verifica");
              }
              if (!herramientaDB.existeEntidad(salaUpdate.getCodigoCine(), PeticionAdminSistema.BUSCAR_CINE.get())) {
                     throw new EntityNotFoundException(
                             "El codigo de cine ingresado no pertenece a ningun cine registrado, verifica"
                     );
              }
              Optional<Sala> salaActual = salasDB.obtenerSala(salaUpdate.getCodigo());

              //si la sala ya esta registrada en una proyeccion se aumenta o mantiene la capacidad
              if (herramientaDB.existeEntidad(salaUpdate.getCodigo(), PeticionesAdminCine.VERIFICAR_SALA_OCUPADA.get())) {
                     if (salaUpdate.getFilas() < salaActual.get().getFilas()) {
                            salaUpdate.setFilas(salaActual.get().getFilas());
                     }
                     if (salaUpdate.getColumnas() < salaActual.get().getColumnas()) {
                            salaUpdate.setColumnas(salaActual.get().getColumnas());
                     }
              }
              salasDB.updateSala(salaUpdate);

              return new SalaResponse(salaUpdate);

       }
       
       @Override
       public void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
             
       }

}
