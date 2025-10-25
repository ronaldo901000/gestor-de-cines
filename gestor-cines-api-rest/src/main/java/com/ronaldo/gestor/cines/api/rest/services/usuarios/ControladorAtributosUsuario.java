package com.ronaldo.gestor.cines.api.rest.services.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.dtos.recargas.RecargaRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.recargas.Recarga;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;

/**
 *
 * @author ronaldo
 */
public class ControladorAtributosUsuario {

       public double obtenerSaldoActual(String idUsuario) throws DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              UsuariosDB usuariosDB = new UsuariosDB();

              if (!herramientaDB.existeEntidad(idUsuario, PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("Usuario no encontrado en la base de datos");
              }
              return usuariosDB.obtenerCreditos(idUsuario);
       }

       /**
        *
        * @param recargaRequest
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        */
       public double recargarCartera(RecargaRequest recargaRequest) throws UserDataInvalidException, DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              UsuariosDB usuariosDB = new UsuariosDB();
              Recarga recarga = extraer(recargaRequest);
              if (!herramientaDB.existeEntidad(recarga.getIdUsuario(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("Usuario no encontrado en la base de datos");
              }
              Usuario usuario = new Usuario();
              usuario.setId(recarga.getIdUsuario());
              
              recarga.setMonto(usuario.calcularNuevoSaldo(recarga.getMonto()));
              //se guarda el nuevo saldo del usuario
              usuariosDB.recargarCartera(recarga);

              //se obtiene y se envia el nuevo moto
              return usuariosDB.obtenerCreditos(recarga.getIdUsuario());
       }
       
       private Recarga extraer(RecargaRequest recargaRequest) throws UserDataInvalidException {
              Recarga recarga = new Recarga();
              recarga.setIdUsuario(recargaRequest.getIdUsuario());
              recarga.setMonto(recargaRequest.getMonto());
              
              if (!recarga.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return recarga;
       }
}
