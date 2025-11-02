package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.db.preciosAnunciosDB.CostoBloqueoRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.preciosAnuncios.PreciosAnunciosRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.preciosAnuncios.CRUDPreciosAnuncios;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author ronaldo
 */
@Path("precioAnuncios")
public class PrecioAnunciosResource {

       @Context
       private UriInfo context;

       
       @GET
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerPreciosAnuncios() {
              CRUDPreciosAnuncios crud = new CRUDPreciosAnuncios();
              try {
                     return Response.ok(crud.obtenerPrecios()).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              }
       }

       @PUT
       @Consumes(MediaType.APPLICATION_JSON)
       public Response actualizarPrecios(PreciosAnunciosRequest preciosAnunciosRequest) {
              CRUDPreciosAnuncios crud = new CRUDPreciosAnuncios();
              try {
                     crud.actualizar(preciosAnunciosRequest);
                     return Response.ok(preciosAnunciosRequest).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (EntityNotFoundException e) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              }
       }

       @PUT
       @Path("costo-bloqueo")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response actualizarPrecioBloqueo(CostoBloqueoRequest nuevoPrecio) {
              CRUDPreciosAnuncios crud = new CRUDPreciosAnuncios();
              try {
                     crud.actualizarCostoBloqueo(nuevoPrecio);
                     return Response.ok().build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              }
       }

       @GET
       @Path("obtener-costo-bloqueo")
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerPrecioBloqueo() {
              CRUDPreciosAnuncios crud = new CRUDPreciosAnuncios();
              try {
                     return Response.ok(crud.obtenerCostoBloqueo()).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              }
       }

}
