package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioImagenRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioTextoRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioVideoRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.SaldoInsuficienteException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.anuncios.CRUDAnuncios;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author ronaldo
 */
@Path("anuncios")
public class AnunciosResource {

       @Context
       private UriInfo context;

       @POST
       @Path("texto")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearAnuncioTexto(AnuncioTextoRequest anucAnuncioRequest) {
              CRUDAnuncios crud = new CRUDAnuncios();
              try {
                     crud.crearAnuncioTexto(anucAnuncioRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (EntityAlreadyExistsException | SaldoInsuficienteException ex) {
                     return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

       @POST
       @Path("video")
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearAnuncioVideo(AnuncioVideoRequest anucAnuncioRequest) {
              CRUDAnuncios crud = new CRUDAnuncios();
              try {
                     crud.crearAnuncioVideo(anucAnuncioRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (EntityAlreadyExistsException | SaldoInsuficienteException ex) {
                     return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

       @POST
       @Path("imagen")
       @Consumes(MediaType.MULTIPART_FORM_DATA)
       public Response crearAnuncioImagen(
               @FormDataParam("codigo") String codigo,
               @FormDataParam("idAnunciante") String idAnunciante,
               @FormDataParam("titulo") String titulo,
               @FormDataParam("tipo") String tipo,
               @FormDataParam("descripcion") String descripcion,
               @FormDataParam("fechaRegistro") String fechaRegistro,
               @FormDataParam("precio") double precio,
               @FormDataParam("duracion") int duracion,
               @FormDataParam("imagen") InputStream imagenFileStream,
               @FormDataParam("imagen") FormDataContentDisposition fileDetails
       ) throws UserDataInvalidException, IOException {
              CRUDAnuncios crud = new CRUDAnuncios();
              AnuncioImagenRequest request;

              request = crud.crearRequestAnuncioImagen(
                      codigo,
                      idAnunciante,
                      titulo,
                      tipo,
                      descripcion,
                      fechaRegistro,
                      precio,
                      duracion,
                      imagenFileStream,
                      fileDetails
              );
              try {
                     crud.crearAnuncioImagen(request);
                     return Response.status(Response.Status.CREATED).build();
              } catch (EntityAlreadyExistsException | SaldoInsuficienteException ex) {
                     return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              }
       }

}
