package com.ronaldo.gestor.cines.api.rest.services.opiniones;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.opinion.OpinionDB;
import com.ronaldo.gestor.cines.api.rest.dtos.opiniones.OpinionRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.opiniones.OpinionResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.opinion.Opinion;
import com.ronaldo.gestor.cines.api.rest.models.opinion.TipoOpinion;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import com.ronaldo.gestor.cines.api.rest.services.usuarios.CRUDUsuarios;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class CRUDOpiniones extends CRUD {

       @Override
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException, EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              OpinionDB opinionDB = new OpinionDB();
              Opinion opinion = (Opinion) extraer(entidadRequest);

              verificarExistenciasEnDB(opinion, opinionDB);

              String query = PeticionUsuario.CREAR_OPINION_PELICULA.get();

              if (opinion.getTipo().equals(TipoOpinion.SALA.name())) {
                     query = PeticionUsuario.CREAR_OPINION_SALA.get();
              }

              opinionDB.crearOpinion(opinion, query);

              return opinion;
       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              OpinionRequest request = (OpinionRequest) entidadRequest;
              Opinion opinion = new Opinion();

              opinion.setCodigoEntidad(request.getCodigoEntidad());
              opinion.setIdUsuario(request.getIdUsuario());
              opinion.setComentario(request.getComentario());
              opinion.setCalificacion(request.getCalificacion());
              opinion.setFecha(request.getFecha());
              opinion.setTipo(request.getTipo());

              if (!opinion.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return opinion;
       }

       @Override
       public EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       /**
        *
        * @param opinion
        * @param opinionDB
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws EntityAlreadyExistsException
        */
       private void verificarExistenciasEnDB(Opinion opinion, OpinionDB opinionDB) throws DataBaseException, EntityNotFoundException, EntityAlreadyExistsException {
              HerramientaDB herramientaDB = new HerramientaDB();

              //se verifica la existencia del usuario
              if (!herramientaDB.existeEntidad(opinion.getIdUsuario(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("Usuario no encontrado en la db");
              }
              //se verifica la existencia de la sala y si ya se le hizo una opinion
              if (opinion.getTipo().equals(TipoOpinion.SALA.name())) {
                     if (!herramientaDB.existeEntidad(opinion.getCodigoEntidad(), PeticionesAdminCine.BUSCAR_SALA.get())
                             && !herramientaDB.existeEntidad(opinion.getCodigoEntidad(), PeticionesAdminCine.BUSCAR_PELICULA.get())) {
                            throw new EntityNotFoundException("Sala no encontrada en la db");
                     }
                     if (opinionDB.existeOpinion(opinion, PeticionUsuario.BUSCAR_MI_OPINION_SALA.get())) {
                            throw new EntityAlreadyExistsException("Ya tienes opinion en esta sala");
                     }

              }

              //se verifica la existencia de la pelicula y si ya se le hizo una opinion
              if (opinion.getTipo().equals(TipoOpinion.PELICULA.name())) {
                     if (!herramientaDB.existeEntidad(opinion.getCodigoEntidad(), PeticionAdminSistema.OBTENER_PELICULA.get())) {
                            throw new EntityNotFoundException("Pelicula no encontrada en la db");
                     }
                     if (opinionDB.existeOpinion(opinion, PeticionUsuario.BUSCAR_MI_OPINION_PELICULA.get())) {
                            throw new EntityAlreadyExistsException("Ya tienes opinion en esta pelicula");
                     }
              }

       }

       /**
        *
        * @param codigo
        * @param esPelicula
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        */
       public List<OpinionResponse> obtenerOpiniones(String codigo, boolean esPelicula) throws
               UserDataInvalidException, DataBaseException, EntityNotFoundException {
              OpinionDB opinionDB = new OpinionDB();

              if (!StringUtils.isNotBlank(codigo)) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              String consulta = PeticionUsuario.OBTENER_OPINIONES_PELCULA.get();
              if (!esPelicula) {
                     consulta = PeticionUsuario.OBTENER_OPINIONES_SALA.get();
              }
              List<Opinion> opiniones = opinionDB.obtenerOpinion(codigo, consulta, esPelicula);

              return crearRespuesta(opiniones);
       }

       /**
        *
        * @param opiniones
        * @return
        * @throws DataBaseException
        * @throws EntityNotFoundException
        */
       private List<OpinionResponse> crearRespuesta(List<Opinion> opiniones) throws DataBaseException, EntityNotFoundException {
              List<OpinionResponse> responses = new ArrayList<>();
              CRUDUsuarios crud = new CRUDUsuarios();

              for (int i = 0; i < opiniones.size(); i++) {
                     OpinionResponse response = new OpinionResponse();

                     response.setCodigoEntidad(opiniones.get(i).getCodigoEntidad());
                     response.setCalificacion(opiniones.get(i).getCalificacion());
                     response.setComentario(opiniones.get(i).getComentario());
                     response.setFecha(opiniones.get(i).getFecha());
                     response.setUsuario(crud.obtenerUsuario(opiniones.get(i).getIdUsuario()));
                     responses.add(response);

              }
              return responses;
       }

}
