package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema;

import com.ronaldo.gestor.cines.api.rest.db.opinion.OpinionDB;
import com.ronaldo.gestor.cines.api.rest.db.reportes.ReportesAdminSistemaDB;
import com.ronaldo.gestor.cines.api.rest.db.salas.SalasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasComentadas.ContadorSalaCometada;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasComentadas.SalaComentada;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.Calificacion;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.SalasGustadas;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines.CalculadorTopSalasGustadas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CreadorSalasPopulares {

       public static final int TOP=5;
       public List<SalasGustadas> obtenerSalasPopulares(FiltroSalasMasPopulares filtro) throws DataBaseException {

              SalasDB salasDB = new SalasDB();
              ReportesAdminSistemaDB reportesAdminSistemaDB = new ReportesAdminSistemaDB();
              CalculadorTopSalasGustadas calculador = new CalculadorTopSalasGustadas();
              List<SalasGustadas> salasGustadas = new ArrayList<>();

              List<Sala> salas = salasDB.obtenerTodasLasSalas();

              for (int i = 0; i < salas.size(); i++) {
                     SalasGustadas salasGustada = generarSalaGustada(salas.get(i), filtro, reportesAdminSistemaDB);
                     if (!salasGustada.getCalificaciones().isEmpty()) {
                            salasGustadas.add(salasGustada);
                     }
              }
              List<SalasGustadas> topCincoPopulares = calculador.obtenerTopSalasGustadas(salasGustadas);

              return topCincoPopulares;

       }

       /**
        *
        * @param sala
        * @param filtro
        * @param reportesAdminSistemaDB
        * @return
        * @throws DataBaseException
        */
       private SalasGustadas generarSalaGustada(Sala sala, FiltroSalasMasPopulares filtro, ReportesAdminSistemaDB reporteAdminSistemaDB)
               throws DataBaseException {
              filtro.generarQuery();
              List<Calificacion> calificaciones = reporteAdminSistemaDB.obtenerCalificacionesSala(sala.getCodigo(), filtro);
              SalasGustadas salasGustadas = new SalasGustadas();
              salasGustadas.setSala(sala);
              salasGustadas.setCalificaciones(calificaciones);
              return salasGustadas;
       }

       /**
        * 
        * @param filtro
        * @return
        * @throws DataBaseException
        */
       public List<SalaComentada> obtenerSalasComentadas(FiltroSalasMasPopulares filtro) throws DataBaseException {
              OpinionDB opinionDB = new OpinionDB();
              List<SalaComentada> salasComentadas = new ArrayList<>();
              SalasDB salasDB = new SalasDB();
              List<String> codigosSalas = opinionDB.obtenerCodigoSalaOpiniones();

              List<ContadorSalaCometada> topSalas = codigosTop5SalasComentadas(codigosSalas);

              for (int i = 0; i < topSalas.size(); i++) {
                     SalaComentada salaComentada = new SalaComentada();
                     salaComentada.setSala(salasDB.obtenerSala(topSalas.get(i).getCodigoSala()).get());
                     salaComentada.setOpiniones(opinionDB.obtenerOpiniones(topSalas.get(i).getCodigoSala(), filtro));
                     if (!salaComentada.getOpiniones().isEmpty()) {
                            salasComentadas.add(salaComentada);
                     }

              }
              return salasComentadas;
       }

       /**
        *
        * @param codigosSalas
        * @return
        */
       private List<ContadorSalaCometada> codigosTop5SalasComentadas(List<String> codigosSalas) {
              List<ContadorSalaCometada> arregloContadores = new ArrayList<>();

              for (int i = 0; i < codigosSalas.size(); i++) {
                     String codigo = codigosSalas.get(i);
                     if (!existeCodigo(arregloContadores, codigo)) {
                            ContadorSalaCometada contadorSalaCometada = new ContadorSalaCometada();
                            contadorSalaCometada.setCodigoSala(codigo);
                            for (int j = 0; j < codigosSalas.size(); j++) {
                                   if (codigosSalas.get(j).equals(codigo)) {
                                          contadorSalaCometada.aumentarContador();
                                   }
                            }
                            arregloContadores.add(contadorSalaCometada);
                     }

              }
              return ordenarDeMayoAMenor(arregloContadores);
       }

       /**
        * 
        * @param arregloContadores
        * @param codigoSala
        * @return 
        */
       private boolean existeCodigo(List<ContadorSalaCometada> arregloContadores, String codigoSala) {
              for (int i = 0; i < arregloContadores.size(); i++) {
                     if (arregloContadores.get(i).getCodigoSala().equals(codigoSala)) {
                            return true;
                     }
              }
              return false;
       }

       /**
        * 
        * @param arregloContadores
        * @return 
        */
       private List<ContadorSalaCometada> ordenarDeMayoAMenor(List<ContadorSalaCometada> arregloContadores) {
              int totalContadores = arregloContadores.size();

              for (int i = 0; i < totalContadores - 1; i++) {
                     for (int j = 0; j < totalContadores - 1 - i; j++) {
                            ContadorSalaCometada actual = arregloContadores.get(j);
                            ContadorSalaCometada siguiente = arregloContadores.get(j + 1);
                            if (siguiente.getVecesComentada() > actual.getVecesComentada()) {
                                   arregloContadores.set(j, siguiente);
                                   arregloContadores.set(j + 1, actual);
                            }
                     }
              }
              if (totalContadores > TOP) {
                     totalContadores = TOP;
              }
              return arregloContadores.subList(0, totalContadores);
       }

}
