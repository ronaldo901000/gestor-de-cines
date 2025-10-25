package com.ronaldo.gestor.cines.api.rest;

import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.services.usuarios.CRUDUsuarios;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author ronaldo
 */
@Path("usuarios")
public class UsuariosResource {

       @Context
       private UriInfo context;

       @GET
       @Path("saldo/{idUsuario}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerSaldoActual(@PathParam("idUsuario") String idUsuario) {
              CRUDUsuarios crud = new CRUDUsuarios();

              try {
                     return Response.ok(crud.obtenerSaldoActual(idUsuario)).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

       
}
