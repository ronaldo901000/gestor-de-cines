package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.salas.SalaRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.salas.CRUDSalas;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ronaldo
 */
@Path("salas")
public class SalasResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearSala(SalaRequest request) {
              CRUDSalas crud = new CRUDSalas();
              try {
                     crud.crear(request);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (EntityAlreadyExistsException ex) {
                     return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

       @GET
       @Path("{codigoCine}/{inicio}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerSalasPorRango(
               @PathParam("codigoCine") String codigoCine,
               @PathParam("inicio") int inicio) {
              CRUDSalas crud = new CRUDSalas();
              try {
                     return Response.ok(crud.obtenerSalasRango(codigoCine, inicio)).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              }
       }

}
