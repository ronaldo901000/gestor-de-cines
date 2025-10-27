package com.ronaldo.gestor.cines.api.rest.services.proyecciones;

import com.ronaldo.gestor.cines.api.rest.db.peliculas.PeliculasDB;
import com.ronaldo.gestor.cines.api.rest.db.salas.SalasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaUpdateResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionResponse;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.proyecciones.Proyeccion;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CreadorProyeccionesResponse {

       /**
        *
        * @param proyecciones
        * @return
        * @throws DataBaseException
        */
       public List<ProyeccionResponse> crearListaResponse(List<Proyeccion> proyecciones) throws DataBaseException {
              List<ProyeccionResponse> proyeccionesResponse = new ArrayList<>();
              for (int i = 0; i < proyecciones.size(); i++) {
                     Proyeccion proyeccion = proyecciones.get(i);
                     ProyeccionResponse response = new ProyeccionResponse();
                     setear(proyeccion, response);
                     proyeccionesResponse.add(response);
              }
              return proyeccionesResponse;
       }

       /**
        * 
        * @param proyeccion
        * @param response
        * @throws DataBaseException 
        */
       private void setear(Proyeccion proyeccion, ProyeccionResponse response) throws DataBaseException {
              PeliculasDB peliculasDB = new PeliculasDB();
              SalasDB salasDB = new SalasDB();
              Optional<PeliculaUpdateResponse> pelicula = peliculasDB.obtenerPelicula(proyeccion.getCodigoPelicula());
              Optional<Sala> sala = salasDB.obtenerSala(proyeccion.getCodigoSala());

              response.setCodigo(proyeccion.getCodigo());
              response.setPelicula(pelicula.get());
              response.setSala(sala.get());
              response.setFecha(proyeccion.getFecha());
              response.setHoraInicio(proyeccion.getHoraInicio());
              response.setHoraFin(proyeccion.getHoraFin());
              response.setPrecio(proyeccion.getPrecio());
              response.setDisponible(proyeccion.isDisponible());
       }

       /**
        * 
        * @param proyeccion
        * @return
        * @throws DataBaseException 
        */
       
       public ProyeccionResponse crearResponse(Proyeccion proyeccion) throws DataBaseException {
              ProyeccionResponse response = new ProyeccionResponse();
              setear(proyeccion, response);
              return response;
       }
}
