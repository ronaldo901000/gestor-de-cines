package com.ronaldo.gestor.cines.api.rest.services.categoria;

import com.ronaldo.gestor.cines.api.rest.db.categoriaDB.CategoriaDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.categorias.CategoriaRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.categorias.CategoriaResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.categorias.Categoria;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CRUDCategorias extends CRUD{

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
              HerramientaDB herramientaDB = new HerramientaDB();
              Categoria categoria = (Categoria) extraer(entidadRequest);
              CategoriaDB categoriaDB= new CategoriaDB();
              
              //si ya existe la categoria se lanza exepcion
              if (herramientaDB.existeEntidad(categoria.getNombre(), PeticionAdminSistema.BUSCAR_CATEGORIA.get())) {
                     throw new EntityAlreadyExistsException("La categoria ya existe en la db");
              }
              
              //se crea la categoria en la db
              categoriaDB.crear(categoria);
              
              return categoria;
       }

       /**
        * 
        * @param entidadRequest
        * @return
        * @throws UserDataInvalidException 
        */
       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              CategoriaRequest categoriaRequest = (CategoriaRequest) entidadRequest;
              
              Categoria categoria = new Categoria();
              categoria.setNombre(categoriaRequest.getNombre());

              if (!categoria.datoValido()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }

              return categoria;
       }

       /**
        * 
        * @return 
        */
       public List<CategoriaResponse> obtenerCategorias() throws DataBaseException {
              CategoriaDB categoriaDB = new CategoriaDB();

              List<Categoria> categorias = categoriaDB.obtener();
              List<CategoriaResponse> categoriasResponse = new ArrayList<>();
              for (int i = 0; i < categorias.size(); i++) {
                     categoriasResponse.add(new CategoriaResponse(categorias.get(i)));
              }
              return categoriasResponse;
       }

       @Override
       protected EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              return null;
       }

       @Override
       protected void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
       }

}
