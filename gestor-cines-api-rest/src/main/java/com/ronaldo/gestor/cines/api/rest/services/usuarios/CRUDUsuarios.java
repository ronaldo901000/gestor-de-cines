package com.ronaldo.gestor.cines.api.rest.services.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.CuentasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.usuarios.UsuarioRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;

/**
 *
 * @author ronaldo
 */
public class CRUDUsuarios extends CRUD {

       @Override
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException, EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              CuentasDB cuentasDB = new CuentasDB();
              Seguridad seguridad = new Seguridad();

              Usuario usuario = (Usuario) extraer(entidadRequest);
              if (herramientaDB.existeEntidad(usuario.getId(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityAlreadyExistsException("Ya existe una cuenta con el ID: " + usuario.getId());
              }

              if (herramientaDB.existeEntidad(usuario.getCorreo(), PeticionUsuario.OBTENER_USUARIO_POR_CORREO.get())) {
                     throw new EntityAlreadyExistsException("Correo no disponible para nueva cuenta");
              }
              //se codifica la contraseña
              usuario.setContraseña(seguridad.encriptarContraseña(usuario.getContraseña()));

              //se crea la cuenta
              cuentasDB.crear(usuario);

              return usuario;
       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              UsuarioRequest request = (UsuarioRequest) entidadRequest;
              Usuario usuario = new Usuario();

              usuario.setId(request.getId());
              usuario.setNombre(request.getNombre());
              usuario.setCorreo(request.getCorreo());
              usuario.setContraseña(request.getContraseña());
              usuario.setTelefono(request.getTelefono());

              if (!usuario.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return usuario;
       }

       @Override
       public EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              return null;
       }

       @Override
       public void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {

       }

}
