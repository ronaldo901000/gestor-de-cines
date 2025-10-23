
package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.proyecciones.CRUDProyecciones;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ronaldo
 */
@Path("proyecciones")
public class ProyeccionesResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearProyeccion(ProyeccionRequest proyeccionRequest) {
              CRUDProyecciones crud = new CRUDProyecciones();
              try {
                     crud.crear(proyeccionRequest);
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

       /**
        *
        * @param codigoCine
        * @param inicio
        * @return
        */
       @GET
       @Path("{codigoCine}/{inicio}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerProyeccionesPorRango(
               @PathParam("codigoCine") String codigoCine,
               @PathParam("inicio") int inicio) {
              CRUDProyecciones crud = new CRUDProyecciones();
              try {
                     return Response.ok(crud.obtenerProyeccionesPorRango(codigoCine, inicio)).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

       @GET
       @Path("{codigo}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response obtenerProyeccion(@PathParam("codigo") String codigo) {
              CRUDProyecciones crud = new CRUDProyecciones();
              try {

                     return Response.ok(crud.obtenerProyeccion(codigo)).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              }
       }

       @PUT
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response actualizarProyeccion(ProyeccionRequest peliculaRequest) {
              CRUDProyecciones crud = new CRUDProyecciones();

              try {
                     return Response.ok(crud.actualizar(peliculaRequest)).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              }
       }

       @DELETE
       @Path("{codigo}")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response deletePelicula(@PathParam("codigo") String codigo) {
              CRUDProyecciones crud = new CRUDProyecciones();

              try {
                     crud.eliminar(codigo);
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
