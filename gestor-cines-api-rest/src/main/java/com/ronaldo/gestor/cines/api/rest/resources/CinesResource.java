package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.cines.CineResponse;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.NuevoCineRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.services.cines.CRUDCines;
import com.ronaldo.gestor.cines.api.rest.services.cines.CreadorCines;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author ronaldo
 */
@Path("cines")
public class CinesResource {

       @Context
       UriInfo uriInfo;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearCine(NuevoCineRequest cineRequest) throws DataBaseException {
              CreadorCines creadorCines = new CreadorCines();
              try {
                     creadorCines.crearCine(cineRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (EntityAlreadyExistsException e) {
                     return Response.status(Response.Status.CONFLICT).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              }
       }

       @GET
       @Path("pagina/{inicio}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getCinesPorPagina(@PathParam("inicio") int inicio) {
              CRUDCines crudCine = new CRUDCines();
              try {
                     List<CineResponse> cines = crudCine.obtenerCines(inicio);
                     return Response.ok(cines).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }
       }

       @GET
       @Path("palabraClave/{palabraClave}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getCinesPorPalabraClave(@PathParam("palabraClave") String palabraClave) {
              CRUDCines crudCine = new CRUDCines();
              try {
                     List<CineResponse> cines = crudCine.obtenerCinesPorNombreOCodigo(palabraClave);
                     return Response.ok(cines).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }
       }
}
