package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaResponse;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.peliculas.CRUDPeliculas;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author ronaldo
 */
@Path("peliculas")
public class PeliculasResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.MULTIPART_FORM_DATA)
       public Response crearPelicula(
               @FormDataParam("codigo") String codigo,
               @FormDataParam("titulo") String titulo,
               @FormDataParam("sinopsis") String sinopsis,
               @FormDataParam("duracion") int duracion,
               @FormDataParam("director") String director,
               @FormDataParam("cast") String cast,
               @FormDataParam("clasificacion") String clasificacion,
               @FormDataParam("fechaEstreno") String fechaEstreno,
               @FormDataParam("idsCategorias") String idsCategorias,
               @FormDataParam("poster") InputStream posterFileStream,
               @FormDataParam("poster") FormDataContentDisposition fileDetails
       ) throws IOException {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();
              PeliculaRequest peliculaRequest;
              try {
                     peliculaRequest = crudPeliculas.construirRequest(
                             codigo,
                             titulo,
                             sinopsis,
                             duracion,
                             director,
                             cast,
                             clasificacion,
                             fechaEstreno,
                             idsCategorias,
                             posterFileStream,
                             fileDetails
                     );
                     
                     crudPeliculas.crear(peliculaRequest);
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
       @Path("especifico/{cadenaBusqueda}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response getPeliculasPorCategoriaOTitulo(@PathParam("cadenaBusqueda") String cadenaBusqueda) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();
              try {
                     return Response.ok(crudPeliculas.obtenerPeliculasPorTituloOCategoria(cadenaBusqueda)).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
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



       @DELETE
       @Path("{codigoPelicula}")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response deletePelicula(@PathParam("codigoPelicula") String codigo) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();

              try {
                     crudPeliculas.eliminar(codigo);
                     return Response.ok().build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              }
       }

       @GET
       @Path("/poster/{codigo}")
       @Produces("image/jpeg")
       public Response getPoster(@PathParam("codigo") String codigo) {
              CRUDPeliculas crudPeliculas = new CRUDPeliculas();

              try {
                     byte[] poster = crudPeliculas.obtenerPoster(codigo);
                     StreamingOutput stream = output -> {
                            try {
                                   output.write(poster);
                                   output.flush();
                            } catch (IOException e) {
                                   throw new WebApplicationException("File Not Found !!");
                            }
                     };

                     return Response.ok(stream)
                             .header("Content-Disposition", "inline; filename=\"" + codigo + ".jpg\"")
                             .build();

              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              }
       }

}
