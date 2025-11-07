package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema;

import com.ronaldo.gestor.cines.api.rest.db.anuncios.AnunciosDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class GeneradorAnunciosAnunciante {

       /**
        *
        * @param filtro
        * @return
        * @throws DataBaseException
        */
       public List<AnuncioResponse> obtenerAnunciosPorAnunciante(FiltroReporteGananciasAnunciante filtro)
               throws DataBaseException {
              List<AnuncioResponse> anunciosFiltrados = new ArrayList<>();
              AnunciosDB anunciosDB = new AnunciosDB();
              List<AnuncioResponse> anuncios = anunciosDB.obtenerAnuncios(filtro.getIdAnunciante());
              if (filtro.getFechaInicio() != null && filtro.getFechaFin() != null) {
                     for (int i = 0; i < anuncios.size(); i++) {
                            if (!anuncios.get(i).getFechaRegistro().isBefore(filtro.getFechaInicio())
                                    && !anuncios.get(i).getFechaRegistro().isAfter(filtro.getFechaFin())) {
                                   anunciosFiltrados.add(anuncios.get(i));
                            }
                     }
              } else {
                     return anuncios;
              }
              return anunciosFiltrados;
       }

       public Usuario generarUsuarioAnunciante(String id) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              return usuariosDB.obteneUsuario(id).get();
       }
}
