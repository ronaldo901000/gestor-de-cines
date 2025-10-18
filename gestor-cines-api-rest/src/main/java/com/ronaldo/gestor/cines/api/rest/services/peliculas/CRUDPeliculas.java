package com.ronaldo.gestor.cines.api.rest.services.peliculas;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.peliculas.PeliculasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
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

/**
 *
 * @author ronaldo
 */
public class CRUDPeliculas  extends CRUD{

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
                            throw new EntityNotFoundException("La categoria indicada no esta registrada en el sistema");
                     }
              }
       }

       @Override
       protected EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }
       
       @Override
       protected void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }
       
}
