package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.credenciales.CredencialRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.CredencialInvalidadException;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.usuarios.GeneradorPropiedadesUsuario;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ronaldo
 */
@Path("autenticaciones")
public class AutenticacionesResource {

       @Context
       private UriInfo context;

       @POST
       @Path("login")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response login(CredencialRequest request) {
              GeneradorPropiedadesUsuario generador = new GeneradorPropiedadesUsuario();
              try {

                     return Response.ok(generador.obtenerPropiedades(request)).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (CredencialInvalidadException ex) {
                     return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
              }
       }

}
