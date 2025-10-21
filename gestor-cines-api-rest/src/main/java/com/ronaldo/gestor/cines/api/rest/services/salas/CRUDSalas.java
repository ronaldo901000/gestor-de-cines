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

       
       @Override
       public EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

}
