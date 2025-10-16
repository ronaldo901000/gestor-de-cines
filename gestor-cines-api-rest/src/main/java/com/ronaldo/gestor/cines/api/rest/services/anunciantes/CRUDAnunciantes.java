package com.ronaldo.gestor.cines.api.rest.services.anunciantes;

import com.ronaldo.gestor.cines.api.rest.db.anunciantes.AnunciantesDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anunciantes.AnuncianteRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.usuarios.Anunciante;
import com.ronaldo.gestor.cines.api.rest.dtos.usuarios.UsuarioResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CRUDAnunciantes extends CRUD {

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
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException, EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              AnunciantesDB anunciantesDB = new AnunciantesDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              Anunciante anunciante = (Anunciante) extraer(entidadRequest);

              if (!herramientaDB.existeEntidad(anunciante.getIdUsuario(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("El usuario no esta registrado en el sistema");
              }
              if (herramientaDB.existeEntidad(anunciante.getIdUsuario(), PeticionAdminSistema.BUSCAR_ANUNCIANTE.get())) {
                     throw new EntityAlreadyExistsException("El usuario, ya esta registrado como anunciante");
              }
              anunciantesDB.crearAnunciante(anunciante.getIdUsuario());
              return anunciante;
       }

       /**
        *
        * @param entidadRequest
        * @return
        * @throws UserDataInvalidException
        */
       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              AnuncianteRequest anuncianteRequest = (AnuncianteRequest) entidadRequest;
              Anunciante anunciante = new Anunciante(anuncianteRequest);
              if (!anunciante.idValido()) {
                     throw new UserDataInvalidException("Error en el ID enviado");
              }
              return anunciante;
       }

       public List<UsuarioResponse> obtenerAnunciantes() throws DataBaseException {
              AnunciantesDB anunciantesDB = new AnunciantesDB();
              List<Usuario> anunciantes = anunciantesDB.obtenerAnunciantes();
              List<UsuarioResponse> anunciantesResponse = new ArrayList<>();

              for (int i = 0; i < anunciantes.size(); i++) {
                     anunciantesResponse.add(new UsuarioResponse(anunciantes.get(i)));
              }
              return anunciantesResponse;
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
