package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.costoGlobal.CostoGlobal;
import com.ronaldo.gestor.cines.api.rest.services.costoGlobal.RUCostoGlobal;
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
@Path("costoGlobal")
public class CostoGlobalResource {

       @Context
       private UriInfo context;

       @GET
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerCostoGlobal() {
              RUCostoGlobal costoGlobal = new RUCostoGlobal();
              try {

                     return Response.ok(costoGlobal.obtenerCostoGlobal()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              }
       }

       @PUT
       @Consumes(MediaType.APPLICATION_JSON)
       public Response actualizarCosto(CostoGlobal costo) {
              RUCostoGlobal costoGlobal = new RUCostoGlobal();
              try {
                     costoGlobal.actualizarCosto(costo.getCosto());
                     return Response.ok().build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              }
       }

}
