package com.ronaldo.gestor.cines.api.rest.services;

import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;

/**
 *
 * @author ronaldo
 */
public abstract class CRUD {

       protected abstract Editable crear(EntidadRequest entidadRequest) throws
               UserDataInvalidException,
               EntityAlreadyExistsException,
               DataBaseException, EntityNotFoundException;

       protected abstract Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException;

       protected abstract EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException,
               EntityNotFoundException, UserDataInvalidException;

       protected abstract void eliminar(String codigo)throws DataBaseException, EntityNotFoundException;
}
