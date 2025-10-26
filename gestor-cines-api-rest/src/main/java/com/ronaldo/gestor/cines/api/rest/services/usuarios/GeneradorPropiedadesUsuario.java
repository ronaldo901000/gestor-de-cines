package com.ronaldo.gestor.cines.api.rest.services.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.adminCine.AdminCineDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.CuentasDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.dtos.credenciales.CredencialRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.credenciales.PropiedadesUsuario;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.roles.Roles;
import com.ronaldo.gestor.cines.api.rest.exceptions.CredencialInvalidadException;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.credenciales.Credencial;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class GeneradorPropiedadesUsuario {

       /**
        * 
        * @param request
        * @return
        * @throws UserDataInvalidException
        * @throws EntityNotFoundException
        * @throws DataBaseException
        * @throws CredencialInvalidadException 
        */
       public PropiedadesUsuario obtenerPropiedades(CredencialRequest request) throws
               UserDataInvalidException, EntityNotFoundException, DataBaseException, CredencialInvalidadException {
              CuentasDB cuentasDB = new CuentasDB();
              Seguridad seguridad = new Seguridad();
              
              Credencial credencial = extraer(request);
              
              Optional<Credencial> credencialCorrecta = cuentasDB.obtenerCredencial(credencial.getCorreo());
              if (credencialCorrecta.isEmpty()) {
                     throw new EntityNotFoundException("El correo no esta registrado en el sistema");
              }

              //verifica si las contraseñas coinciden
              seguridad.verificarCredenciales(credencial, credencialCorrecta.get());
              
              //se devuelven las propiedades del usuario que iran en al local storage
              return crearPropiedades(credencial.getCorreo());

       }

       /**
        * 
        * @param request
        * @return
        * @throws UserDataInvalidException 
        */
       private Credencial extraer(CredencialRequest request) throws UserDataInvalidException {
              Credencial credencial = new Credencial();
              credencial.setCorreo(request.getCorreo());
              credencial.setContraseña(request.getContraseña());

              if (!credencial.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return credencial;
       }

       /**
        * 
        * @param correo
        * @return
        * @throws DataBaseException 
        */
       private PropiedadesUsuario crearPropiedades(String correo) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              Optional<Usuario> usuario = usuariosDB.obtenerUsuarioPorCorreo(correo);
              PropiedadesUsuario propiedades = new PropiedadesUsuario();

              propiedades.setId(usuario.get().getId());
              propiedades.setRole(obtenerRol(usuario.get().getId()));
              propiedades.setEsAnunciante(esAnunciante(usuario.get().getId()));

              //se agrega el codigo del cine donde trabaja
              if (propiedades.getRole().equals(Roles.ADMIN_CINE.getRol())) {
                     propiedades.setCodigoCine(obtenerCodigoCine(usuario.get().getId()));
              }
              return propiedades;
       }

       /**
        * 
        * @param id
        * @return
        * @throws DataBaseException 
        */
       private String obtenerRol(String id) throws DataBaseException {
              HerramientaDB herramientaDB = new HerramientaDB();
              if (herramientaDB.existeEntidad(id, PeticionAdminSistema.OBTENER_ADMIN_SISTEMA.get())) {
                     return Roles.ADMIN_SISTEMA.getRol();
              } else if (herramientaDB.existeEntidad(id, PeticionAdminSistema.OBTENER_ADMIN_CINE.get())) {
                     return Roles.ADMIN_CINE.getRol();
              }
              return Roles.USUARIO_NORMAL.getRol();
       }

       /**
        * 
        * @param id
        * @return
        * @throws DataBaseException 
        */
       private boolean esAnunciante(String id) throws DataBaseException {
              HerramientaDB herramientaDB = new HerramientaDB();
              return herramientaDB.existeEntidad(id, PeticionAdminSistema.OBTENER_UN_ANUNCIANTE.get());
       }

       /**
        * 
        * @param idUsuario
        * @return
        * @throws DataBaseException 
        */
       private String obtenerCodigoCine(String idUsuario) throws DataBaseException {
              AdminCineDB adminCineDB = new AdminCineDB();
              Optional<Cine> cine = adminCineDB.obtenerCine(idUsuario);

              return cine.get().getCodigo();
       }
}
