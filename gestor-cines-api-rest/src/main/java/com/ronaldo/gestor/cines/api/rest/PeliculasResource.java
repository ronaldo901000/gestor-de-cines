package com.ronaldo.gestor.cines.api.rest;

import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaResponse;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.peliculas.CRUDPeliculas;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ronaldo
 */
@Path("peliculas")
public class PeliculasResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearPelicula(PeliculaRequest peliculaRequest) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();
              try {
                     crudPeliculas.crear(peliculaRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (EntityAlreadyExistsException ex) {
                     return Response.status(Response.Status.CONFLICT).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              }
       }

       @GET
       @Path("paginacion/{inicio}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getPeliculas(@PathParam("inicio") int inicio) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();

              try {
                     return Response.ok(crudPeliculas.obtenerPeliculas(inicio)).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }
       }

       @GET
       @Path("pelicula/{codigo}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getPeliculas(@PathParam("codigo") String codigo) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();
              try {

                     return Response.ok(crudPeliculas.obtenerPelicula(codigo)).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              }
       }

       @PUT
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response updatePelicula(PeliculaRequest peliculaRequest) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();

              try {
                     PeliculaResponse pelicula = (PeliculaResponse) crudPeliculas.actualizar(peliculaRequest);
                     return Response.ok(pelicula).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              }
       }

}
