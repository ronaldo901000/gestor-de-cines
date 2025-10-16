package com.ronaldo.gestor.cines.api.rest.services.adminCine;

import com.ronaldo.gestor.cines.api.rest.db.adminCine.AdminCineDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.adminCine.AdminCineRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.usuarios.UsuarioResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.adminCine.AdminCine;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class CRUDAdminCine extends CRUD {

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
       public Editable crear(EntidadRequest entidadRequest) throws
               UserDataInvalidException, EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              AdminCine adminCine = (AdminCine) extraer(entidadRequest);
              AdminCineDB adminCineDB = new AdminCineDB();

              //se verifica la existencia del cine y del usuario, o si el usuario ya es admin de cine o de sistema
              if (!herramientaDB.existeEntidad(adminCine.getId(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("El Id obtenido no corresponde a ningun usuario");
              }

              if (herramientaDB.existeEntidad(adminCine.getId(), PeticionAdminSistema.OBTENER_ADMIN_CINE.get())
                      || herramientaDB.existeEntidad(adminCine.getId(), PeticionAdminSistema.OBTENER_ADMIN_SISTEMA.get())) {
                     throw new EntityAlreadyExistsException("El usuario ya es admin de cine o sistema");
              }
              if (!herramientaDB.existeEntidad(adminCine.getCodigoCine(), PeticionAdminSistema.OBTENER_CINE_POR_CODIGO.get())) {
                     throw new EntityNotFoundException("El cine indicado no existe en la base de datos");
              }
              //se crea el admin de cine
              adminCineDB.crearAdmin(adminCine);
              return adminCine;
       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              AdminCineRequest adminCineRequest = (AdminCineRequest) entidadRequest;
              AdminCine adminCine = new AdminCine();
              adminCine.setId(adminCineRequest.getId());
              adminCine.setCodigoCine(adminCineRequest.getCodigoCine());
              return adminCine;
       }

       /**
        *
        * @param codigo
        * @return
        * @throws DataBaseException
        */
       public List<UsuarioResponse> obtenerAdmins(String codigo) throws DataBaseException {
              List<UsuarioResponse> adminsResponse = new ArrayList<>();
              AdminCineDB adminCineDB = new AdminCineDB();
              List<Usuario> admins = adminCineDB.obtenerAdminsCine(codigo);
              
              for (int i = 0; i < admins.size(); i++) {
                     adminsResponse.add(new UsuarioResponse(admins.get(i)));
              }
              return adminsResponse;
       }

       @Override
       protected EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       /**
        * 
        * @param idUsuario
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws UserDataInvalidException 
        */
       @Override
       public void eliminar(String idUsuario) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              HerramientaDB herramientaDB= new HerramientaDB();
              AdminCineDB adminCineDB= new AdminCineDB();
              if(StringUtils.isBlank(idUsuario)){
                     throw new UserDataInvalidException("Error en el id enviado");
              }
              if(!herramientaDB.existeEntidad(idUsuario, PeticionAdminSistema.OBTENER_USUARIO.get())){
                     throw new EntityNotFoundException("El usuario no esta registrado en el sistema");
              }
              adminCineDB.eliminar(idUsuario);
       }

}
