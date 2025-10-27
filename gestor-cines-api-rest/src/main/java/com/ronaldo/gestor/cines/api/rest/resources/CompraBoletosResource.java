package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.compraBoletos.CompraBoletosRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.FaltaBoletosException;
import com.ronaldo.gestor.cines.api.rest.exceptions.SaldoInsuficienteException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.services.compraBoletos.CRCompraBoletos;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author ronaldo
 */
@Path("compraBoletos")
public class CompraBoletosResource {

       @Context
       private UriInfo context;

       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       public Response crearCompraBoletos(CompraBoletosRequest compraBoletosRequest) {
              CRCompraBoletos compraBoletos = new CRCompraBoletos();
              try {
                     compraBoletos.crearCompra(compraBoletosRequest);
                     return Response.status(Response.Status.CREATED).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
              } catch (FaltaBoletosException | SaldoInsuficienteException ex) {
                     return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
              }
       }
}
