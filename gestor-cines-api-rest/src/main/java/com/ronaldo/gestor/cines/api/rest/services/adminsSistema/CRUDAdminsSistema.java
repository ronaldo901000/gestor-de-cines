package com.ronaldo.gestor.cines.api.rest.services.adminsSistema;

import com.ronaldo.gestor.cines.api.rest.db.adminSistema.AdminSistemaDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.adminsSistema.AdminSistemaRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.usuarios.UsuarioResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.adminsSistema.AdminSistema;
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
public class CRUDAdminsSistema extends CRUD {

       @Override
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException, EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              AdminSistemaDB adminSistemaDB = new AdminSistemaDB();
              AdminSistema adminSistema = (AdminSistema) extraer(entidadRequest);
              //si no es usuario, expandir excepcion
              if (!herramientaDB.existeEntidad(adminSistema.getId(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("El id " + adminSistema.getId() + " no pertenece a ningun usuario");
              }
              //si ya es admin de sistema o cine, lanzar excepcion
              if (herramientaDB.existeEntidad(adminSistema.getId(), PeticionAdminSistema.OBTENER_ADMIN_CINE.get())
                      || herramientaDB.existeEntidad(adminSistema.getId(), PeticionAdminSistema.OBTENER_ADMIN_SISTEMA.get())) {
                     throw new EntityAlreadyExistsException("El usuario ya es un admin de sistema o de cine");
              }

              adminSistemaDB.crearAdmin(adminSistema);
              return adminSistema;
       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              AdminSistemaRequest request = (AdminSistemaRequest) entidadRequest;
              AdminSistema admin = new AdminSistema();
              admin.setId(request.getId());
              if (!admin.datoValido()) {
                     throw new UserDataInvalidException("Error en el id enviado, verifica");
              }
              return admin;
       }


       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public List<UsuarioResponse> obtenerAdmins() throws DataBaseException {
              List<UsuarioResponse> adminsResponse = new ArrayList<>();
              AdminSistemaDB adminSistemaDB = new AdminSistemaDB();
              List<Usuario> admins = adminSistemaDB.obtenerAdmins();

              for (int i = 0; i < admins.size(); i++) {
                     adminsResponse.add(new UsuarioResponse(admins.get(i)));
              }
              return adminsResponse;
       }

       @Override
       protected EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              return null;
       }

       @Override
       public void eliminar(String id) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              HerramientaDB herramientaDB = new HerramientaDB();
              AdminSistemaDB adminSistemaDB = new AdminSistemaDB();
              List<Usuario> admins = adminSistemaDB.obtenerAdmins();
              if(admins.size()==1){
                     throw new DataBaseException("No se puede eliminar al admin, solo queda uno");
              }
              if (StringUtils.isBlank(id)) {
                     throw new UserDataInvalidException("Error en el id enviado");
              }
              if (!herramientaDB.existeEntidad(id, PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("El usuario no esta registrado en el sistema");
              }
              adminSistemaDB.eliminar(id);
       }

}
