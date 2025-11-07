package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines;

import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.SalasGustadas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CalculadorTopSalasGustadas {

       private static final int TOP = 5;

       public List<SalasGustadas> obtenerTopSalasGustadas(List<SalasGustadas> salasGustadas) {

              //se calcula el promedio a cada sala
              for (SalasGustadas sala : salasGustadas) {
                     double promedio = sala.calcularPromedioCalificacion();
                     sala.setPromedio(promedio);
              }

              //se ordena por el metodo de ordenacion de burbuja
              for (int i = 0; i < salasGustadas.size() - 1; i++) {
                     for (int j = i + 1; j < salasGustadas.size(); j++) {
                            if (salasGustadas.get(i).getPromedio() < salasGustadas.get(j).getPromedio()) {
                                   SalasGustadas salaGustada = salasGustadas.get(i);
                                   salasGustadas.set(i, salasGustadas.get(j));
                                   salasGustadas.set(j, salaGustada);
                            }
                     }
              }

              //se agrega a la lista las 5 mas gustadas
              List<SalasGustadas> topSalas = new ArrayList<>();
              for (int i = 0; i < salasGustadas.size() && i < TOP; i++) {
                     topSalas.add(salasGustadas.get(i));
              }
              return topSalas;
       }

}
