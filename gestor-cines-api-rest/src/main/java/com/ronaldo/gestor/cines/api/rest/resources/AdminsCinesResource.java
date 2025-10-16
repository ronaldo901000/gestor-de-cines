package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.adminCine.AdminCineRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.adminCine.CRUDAdminCine;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author ronaldo
 */
@Path("adminsCines")
public class AdminsCinesResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearAdminCine(AdminCineRequest adminCineRequest) {
              CRUDAdminCine crudAdminCine = new CRUDAdminCine();
              try {
                     crudAdminCine.crear(adminCineRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (EntityAlreadyExistsException e) {
                     return Response.status(Response.Status.CONFLICT).build();
              } catch (EntityNotFoundException e) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              }
       }
       
       @GET
       @Path("{codigoCine}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerAdmins(@PathParam("codigoCine") String codigo) {
              CRUDAdminCine crudAdminCine = new CRUDAdminCine();

              try {
                     return Response.ok(crudAdminCine.obtenerAdmins(codigo)).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              }
       }

}
