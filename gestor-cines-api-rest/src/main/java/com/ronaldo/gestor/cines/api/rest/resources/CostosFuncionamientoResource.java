package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.costosFuncionamiento.CostoFuncionamientoCineRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.costosFuncionamiento.CRUDCostosFuncionamiento;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ronaldo
 */
@Path("costosFuncionamiento")
public class CostosFuncionamientoResource {

       @Context
       private UriInfo context;

       /**
        * 
        * @param request
        * @return 
        */
       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearCostoFuncionamiento(CostoFuncionamientoCineRequest request) {
              CRUDCostosFuncionamiento costosFuncionamiento = new CRUDCostosFuncionamiento();
              try {
                     costosFuncionamiento.crear(request);
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
       @Path("palabraClave/{palabraClave}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getCinesPorPalabraClave(@PathParam("palabraClave") String palabraClave) {
              CRUDCostosFuncionamiento crudCostos = new CRUDCostosFuncionamiento();
              try {
                     return Response.ok(crudCostos.obtenerCinesPorNombreOCodigo(palabraClave)).build();
              } catch (DataBaseException e) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException e) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }
       }

       @DELETE
       @Path("{id}")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response deleteCostoFuncionamiento(@PathParam("id") String id) {
              CRUDCostosFuncionamiento crudCostos = new CRUDCostosFuncionamiento();

              try {
                     crudCostos.eliminar(id);
                     return Response.ok().build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              }
       }

}
