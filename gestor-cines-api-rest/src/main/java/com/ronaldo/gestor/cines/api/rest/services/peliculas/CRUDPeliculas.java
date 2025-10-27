package com.ronaldo.gestor.cines.api.rest.services.peliculas;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.peliculas.PeliculasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaUpdateResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.RangoBusquedaElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.peliculas.Pelicula;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CRUDPeliculas extends CRUD {
       private static final String NOMBRE="nombre";
       private static final String ID="id";
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
              PeliculasDB peliculasDB = new PeliculasDB();

              Pelicula pelicula = (Pelicula) extraer(entidadRequest);
              if (herramientaDB.existeEntidad(pelicula.getCodigo(), PeticionAdminSistema.OBTENER_PELICULA.get())) {
                     throw new EntityAlreadyExistsException("La pelicula ya ha sido registrada antes");
              }
              existenSusCategorias(pelicula.getIdsCategorias());

              //crear en la db
              peliculasDB.crear(pelicula);
              return pelicula;
       }

       /**
        *
        * @param entidadRequest
        * @return
        * @throws UserDataInvalidException
        */
       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              PeliculaRequest peliculaRequest = (PeliculaRequest) entidadRequest;
              Pelicula pelicula = new Pelicula();
              pelicula.setCodigo(peliculaRequest.getCodigo().trim());
              pelicula.setTitulo(peliculaRequest.getTitulo().trim());
              pelicula.setSinopsis(peliculaRequest.getSinopsis().trim());
              pelicula.setDuracion(peliculaRequest.getDuracion());
              pelicula.setDirector(peliculaRequest.getDirector().trim());
              pelicula.setCast(peliculaRequest.getCast().trim());
              pelicula.setClasificacion(peliculaRequest.getClasificacion().trim());
              pelicula.setFechaEstreno(peliculaRequest.getFechaEstreno());
              pelicula.setIdsCategorias(extraerIDsCategorias(peliculaRequest));

              if (!pelicula.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }

              return pelicula;
       }

       /**
        *
        * @param peliculaRequest
        * @return
        * @throws UserDataInvalidException
        */
       private List<String> extraerIDsCategorias(PeliculaRequest peliculaRequest) throws UserDataInvalidException {
              List<String> ids = new ArrayList<>();

              if (peliculaRequest.getIdsCategorias() == null) {
                     throw new UserDataInvalidException("Error, se se enviaron las categorias");
              }
              String idsTexto = peliculaRequest.getIdsCategorias();
              
              if(idsTexto.charAt(0)==','){
                     throw new UserDataInvalidException("Por favor ingresa ids de categorias validas");
              }
              
              String[] idsArray = idsTexto.split(",");
              for (String idElemento : idsArray) {
                     if (seAgregaCategoria(ids, idElemento)) {
                            ids.add(idElemento.trim());
                     }
              }
              return ids;
       }

       private boolean seAgregaCategoria(List<String> ids, String idElemento) {
              for (int i = 0; i < ids.size(); i++) {
                     if (ids.get(i).equals(idElemento)) {
                            return false;
                     }
              }
              return true;
       }

       /**
        *
        * @param idsCategorias
        * @throws DataBaseException
        */
       private void existenSusCategorias(List<String> idsCategorias) throws DataBaseException,
               EntityNotFoundException {

              HerramientaDB herramientaDB = new HerramientaDB();
              for (int i = 0; i < idsCategorias.size(); i++) {
                     if (!herramientaDB.existeCategoria(idsCategorias.get(i))) {
                            throw new EntityNotFoundException("El id '"+idsCategorias.get(i)+"' no esta registrado como una categoria en el sistema");
                     }
              }
       }

       public List<PeliculaResponse> obtenerPeliculas(int inicio) throws DataBaseException, UserDataInvalidException {
              List<PeliculaResponse> peliculasResponse = new ArrayList<>();
              PeliculasDB peliculasDB = new PeliculasDB();
              try {
                     int inicio1 = inicio;
              } catch (NumberFormatException e) {
                     throw new UserDataInvalidException();
              }

              List<Pelicula> peliculas = peliculasDB.obtenerPeliculasPorRango(
                      inicio,
                      inicio + RangoBusquedaElemento.PELICULAS.getRango()
              );

              crearResponse(peliculas, peliculasResponse);
              return peliculasResponse;
       }

       public List<PeliculaResponse> obtenerPeliculasPorTituloOCategoria(String cadenaBusqueda) throws DataBaseException, UserDataInvalidException {
              List<PeliculaResponse> peliculasResponse = new ArrayList<>();
              PeliculasDB peliculasDB = new PeliculasDB();
              
              List<Pelicula> peliculas = peliculasDB.obtenerPeliculasPorTituloOCaregoria(cadenaBusqueda);

              crearResponse(peliculas, peliculasResponse);
              return peliculasResponse;
       }

       /**
        *
        * @param peliculas
        * @param peliculasResponse
        * @throws DataBaseException
        */
       private void crearResponse(List<Pelicula> peliculas, List<PeliculaResponse> peliculasResponse) throws DataBaseException {
              PeliculasDB peliculasDB = new PeliculasDB();
              for (int i = 0; i < peliculas.size(); i++) {
                     peliculasResponse.add(
                             new PeliculaResponse(
                                     peliculas.get(i),
                                     peliculasDB.obtenerCategoriasPelicula(peliculas.get(i).getCodigo(),NOMBRE)
                             )
                     );
              }
       }

       /**
        * 
        * @param codigo
        * @return
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       public PeliculaUpdateResponse obtenerPelicula(String codigo) throws DataBaseException, EntityNotFoundException {
              PeliculasDB peliculasDB = new PeliculasDB();
              Optional<PeliculaUpdateResponse> pelicula = peliculasDB.obtenerPelicula(codigo);
              if (pelicula.isEmpty()) {
                     throw new EntityNotFoundException("No se encontró la pelicula en la db");
              }
              //se cargan los id
              String ids = peliculasDB.obtenerCategoriasPelicula(codigo, ID);
              pelicula.get().setIdsCategorias(ids.substring(0, ids.length() - 1));
              return pelicula.get();
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
       public EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              HerramientaDB herramientaDB = new HerramientaDB();
              PeliculasDB peliculasDB = new PeliculasDB();

              Pelicula pelicula = (Pelicula) extraer(entidadRequest);

              if (!pelicula.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              if (!herramientaDB.existeEntidad(pelicula.getCodigo(), PeticionAdminSistema.OBTENER_PELICULA.get())) {
                     throw new EntityNotFoundException("Error, no se encontro ninguna pelicula con ese codigo");
              }
              existenSusCategorias(pelicula.getIdsCategorias());
              //se actualiza la pelicula en la db
              peliculasDB.actualizar(pelicula);
              
              return obtenerResponse(pelicula);
       }

       /**
        * 
        * @param pelicula
        * @return
        * @throws DataBaseException 
        */
       private PeliculaResponse obtenerResponse(Pelicula pelicula) throws DataBaseException {
              PeliculasDB peliculasDB = new PeliculasDB();
              return new PeliculaResponse(pelicula, peliculasDB.obtenerCategoriasPelicula(pelicula.getCodigo(),NOMBRE));
       }
       
       /**
        * 
        * @param codigo
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws UserDataInvalidException 
        */
       @Override
       public void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              PeliculasDB peliculasDB = new PeliculasDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              if (!herramientaDB.existeEntidad(codigo, PeticionAdminSistema.OBTENER_PELICULA.get())) {
                     throw new EntityNotFoundException("No se encontro la pelicula con codigo " + codigo + " en el sistema");
              }
              peliculasDB.eliminar(codigo);
       }
       
}
