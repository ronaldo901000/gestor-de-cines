package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.cines.CineResponse;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.CineRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.recargas.RecargaCineRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.services.cines.CRUDCines;
import com.ronaldo.gestor.cines.api.rest.services.cines.CarteraCineServices;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
       public Response crearCine(CineRequest cineRequest) throws DataBaseException {
              CRUDCines creadorCines = new CRUDCines();
              try {
                     creadorCines.crear(cineRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
              } catch (EntityAlreadyExistsException e) {
                     return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
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

       @PUT
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response actualizarCine(CineRequest cineRequest) {
              CRUDCines crudCines = new CRUDCines();
              try {
                     CineResponse cineActualizado = crudCines.actualizar(cineRequest);
                     return Response.ok(cineActualizado).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (EntityNotFoundException e) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }
       }

       @DELETE
       @Path("{codigoCine}")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response deleteCine(@PathParam("codigoCine") String codigo) {
              CRUDCines crudCines = new CRUDCines();
              try {
                     crudCines.eliminar(codigo);
                     return Response.ok().build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
              } catch (EntityNotFoundException e) {
                     return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
              }
       }

       @GET
       @Path("saldo-actual/{codigoCine}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getSaldoActual(@PathParam("codigoCine") String codigoCine) {
              CarteraCineServices service = new CarteraCineServices();

              try {

                     return Response.ok(service.obtenerSaldoActual(codigoCine)).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }
       
       @PUT
       @Path("recargas")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response recargarCartera(RecargaCineRequest request) {
              CarteraCineServices service = new CarteraCineServices();

              try {
                     return Response.ok(service.recargarCartera(request)).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

}
