package com.ronaldo.gestor.cines.api.rest.services.compraBoletos;

import com.ronaldo.gestor.cines.api.rest.db.compraBoletos.CompraBoletosDB;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;

/**
 *
 * @author ronaldo
 */
public class DisponibilidadBoletos {
       
       /**
        * 
        * @param codigoProyeccion
        * @param capacidadSala
        * @return
        * @throws DataBaseException 
        */
       public int contarBoletosDisponibles(String codigoProyeccion, int capacidadSala) throws DataBaseException {
              CompraBoletosDB compraBoletosDB = new CompraBoletosDB();
              //se obtiene la cantidad de boletos vendidos
              int cantidadVendidos = compraBoletosDB.obtenerBoletosVendidos(codigoProyeccion);

              return capacidadSala - cantidadVendidos;
       }

}
