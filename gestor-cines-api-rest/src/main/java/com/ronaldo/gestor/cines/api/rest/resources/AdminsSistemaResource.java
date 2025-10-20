package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.adminsSistema.AdminSistemaRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.adminsSistema.CRUDAdminsSistema;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 *
 * @author ronaldo
 */
@Path("adminsSistema")
public class AdminsSistemaResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearAdmin(AdminSistemaRequest request) {
              CRUDAdminsSistema crud = new CRUDAdminsSistema();
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
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerAdmins() {
              CRUDAdminsSistema crud = new CRUDAdminsSistema();
              try {
                     return Response.ok(crud.obtenerAdmins()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              }
       }

       @DELETE
       @Path("{idUsuario}")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response eliminarAdmin(@PathParam("idUsuario") String id) {
              CRUDAdminsSistema crudAdminCines = new CRUDAdminsSistema();
              try {
                     crudAdminCines.eliminar(id);
                     return Response.ok().build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
              } catch (EntityNotFoundException e) {
                     return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
              }
       }

}
