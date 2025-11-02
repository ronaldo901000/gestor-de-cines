package com.ronaldo.gestor.cines.api.rest.services.preciosAnuncios;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.preciosAnunciosDB.CostoBloqueoRequest;
import com.ronaldo.gestor.cines.api.rest.db.preciosAnunciosDB.PreciosAnunciosDB;
import com.ronaldo.gestor.cines.api.rest.dtos.preciosAnuncios.PreciosAnunciosRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.preciosAnuncios.PreciosAnunciosResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.preciosAnuncios.PrecioAnuncio;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CRUDPreciosAnuncios {

       private static final int MAXIMO=100000000;
       private static final int MINIMO=0;
       public PreciosAnunciosResponse[] obtenerPrecios() throws DataBaseException {

              PreciosAnunciosDB preciosAnunciosDB = new PreciosAnunciosDB();

              PreciosAnunciosResponse[] preciosResponse = new PreciosAnunciosResponse[3];

              List<PrecioAnuncio> preciosObtenidos = preciosAnunciosDB.obtenerPrecios();

              for (int i = 0; i < preciosObtenidos.size(); i++) {
                     preciosResponse[i] = new PreciosAnunciosResponse(preciosObtenidos.get(i));
              }
              return preciosResponse;
       }

       /**
        * 
        * @param entidadRequest
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws UserDataInvalidException 
        */
       public void actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              PreciosAnunciosRequest anunciosRequest = (PreciosAnunciosRequest) entidadRequest;
              PreciosAnunciosDB preciosAnunciosDB = new PreciosAnunciosDB();
              PrecioAnuncio precioAnuncio = extraer(anunciosRequest);
              HerramientaDB herramientaDB = new HerramientaDB();

              if (!herramientaDB.existeEntidad(precioAnuncio.getId() + "", PeticionAdminSistema.OBTENER_PRECIO_ANUNCIO.get())) {
                     throw new EntityNotFoundException("EL id del precio no esta registrado en la db");
              }

              preciosAnunciosDB.actualizar(precioAnuncio);
       }

       /**
        *
        * @param anunciosRequest
        * @return
        * @throws UserDataInvalidException 
        */
       private PrecioAnuncio extraer(PreciosAnunciosRequest anunciosRequest) throws UserDataInvalidException {
              PrecioAnuncio precioAnuncio = new PrecioAnuncio();
              precioAnuncio.setId(anunciosRequest.getId());
              precioAnuncio.setPrecioVentaPorDia(anunciosRequest.getPrecioVentaPorDia());
              precioAnuncio.setPrecioBloqueoPorDia(anunciosRequest.getPrecioBloqueoPorDia());

              if (!precioAnuncio.validoEnCampos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return precioAnuncio;
       }

       /**
        * 
        * @param nuevoCosto
        * @throws DataBaseException
        * @throws UserDataInvalidException 
        */
       public void actualizarCostoBloqueo(CostoBloqueoRequest nuevoCosto) throws DataBaseException, UserDataInvalidException {
              PreciosAnunciosDB preciosAnunciosDB = new PreciosAnunciosDB();
              try {
                     
                     if (nuevoCosto.getCostoBloqueo() < MINIMO || nuevoCosto.getCostoBloqueo() > MAXIMO) {
                            throw new UserDataInvalidException("Valor fuera del rango permitido");
                     }
              } catch (NumberFormatException e) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              preciosAnunciosDB.actualizarCostoBloqueo(nuevoCosto.getCostoBloqueo());
       }

       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public double obtenerCostoBloqueo() throws DataBaseException {
              PreciosAnunciosDB preciosAnunciosDB = new PreciosAnunciosDB();
              return preciosAnunciosDB.obtenerCostoBloqueo();
       }

}
