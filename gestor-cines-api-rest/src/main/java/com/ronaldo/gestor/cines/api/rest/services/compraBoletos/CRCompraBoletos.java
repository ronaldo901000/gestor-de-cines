package com.ronaldo.gestor.cines.api.rest.services.compraBoletos;

import com.ronaldo.gestor.cines.api.rest.db.compraBoletos.CompraBoletosDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.dtos.compraBoletos.CompraBoletosRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.compraBoletos.CompraBoletosResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.enums.query.RangoBusquedaElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.FaltaBoletosException;
import com.ronaldo.gestor.cines.api.rest.exceptions.SaldoInsuficienteException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.compraBoletos.CompraBoletos;
import com.ronaldo.gestor.cines.api.rest.services.proyecciones.CRUDProyecciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CRCompraBoletos {
   
       public void crearCompra(CompraBoletosRequest request) throws UserDataInvalidException, 
               DataBaseException, EntityNotFoundException, FaltaBoletosException, SaldoInsuficienteException{
              
              CompraBoletosDB compraBoletosDB = new CompraBoletosDB();

              CompraBoletos compra = extraerDatos(request);
              int boletosDisponibles = verificiarExistenciasEnDB(compra);

              boolean sigueDisponible = true;
              if (boletosDisponibles == 0) {
                     sigueDisponible = false;
              }
              //se realiza la transaccion en la db
              compraBoletosDB.crearCompra(compra, sigueDisponible);
 
       }

       /**
        * 
        * @param compra
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       private int verificiarExistenciasEnDB(CompraBoletos compra) throws DataBaseException,
               EntityNotFoundException,
               FaltaBoletosException,
               SaldoInsuficienteException {
              DisponibilidadBoletos disponibilidadBoletos = new DisponibilidadBoletos();
              verificarExistenciaUsuarioYProyeccion(compra);

              //se verifica que existan la cantidad de boletos disponible
              ProyeccionResponse proyeccion = verificarDisponibilidadBoletos(
                      compra.getCodigoProyeccion(),
                      compra.getCantidad());

              //se verifican que se cuente con los creditos necesarios
              cuentaConSaldoSuficiente(compra, proyeccion);

              int boletosDisponibles = disponibilidadBoletos.contarBoletosDisponibles(
                      compra.getCodigoProyeccion(),
                      proyeccion.getSala().obtenerCapacidad());
              
              return boletosDisponibles-compra.getCantidad();

       }

       /**
        *
        * @param codigoProyeccion
        * @param totalBoletosSolicitados
        * @return
        * @throws EntityNotFoundException
        * @throws DataBaseException
        * @throws FaltaBoletosException 
        */
       private ProyeccionResponse verificarDisponibilidadBoletos(String codigoProyeccion, int totalBoletosSolicitados) throws EntityNotFoundException,
               DataBaseException,
               FaltaBoletosException {

              CRUDProyecciones crud = new CRUDProyecciones();
              DisponibilidadBoletos disponibilidadBoletos = new DisponibilidadBoletos();

              ProyeccionResponse proyeccionResponse = crud.obtenerProyeccion(codigoProyeccion);

              if (!proyeccionResponse.isDisponible()) {
                     throw new FaltaBoletosException("Ya no existen boletos disponbles en esta proyeccion de pelicula");
              }

              int capacidadSala = proyeccionResponse.getSala().obtenerCapacidad();

              int boletosDisponibles = disponibilidadBoletos.contarBoletosDisponibles(
                      codigoProyeccion,
                      capacidadSala);
              
              if (totalBoletosSolicitados > boletosDisponibles) {
                     throw new FaltaBoletosException(
                             "Solo hay disponibles: " + boletosDisponibles + " boletos quieres comprarlos?");
              }
              return proyeccionResponse;
       }

       /**
        * 
        * @param compra
        * @param proyeccion
        * @throws DataBaseException
        * @throws SaldoInsuficienteException 
        */
       private void cuentaConSaldoSuficiente(CompraBoletos compra, ProyeccionResponse proyeccion) throws DataBaseException, SaldoInsuficienteException {
              UsuariosDB usuariosDB = new UsuariosDB();
              compra.setPrecioPorBoleto(proyeccion.getPrecio());

              double saldoDisponible = usuariosDB.obtenerCreditos(compra.getIdUsuario());

              if (!compra.cuentaConSaldoDisponible(saldoDisponible)) {
                     throw new SaldoInsuficienteException(
                             "No cuentas con creditos suficientes para realizar la compra"
                     );
              }
       }

       /**
        *
        * @param compra
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       private void verificarExistenciaUsuarioYProyeccion(CompraBoletos compra) throws DataBaseException, 
               EntityNotFoundException {
              
              HerramientaDB herramientaDB = new HerramientaDB();

              if (!herramientaDB.existeEntidad(compra.getIdUsuario(), PeticionUsuario.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException(
                             "No existe usuario con el id: " + compra.getIdUsuario() + " en el sistema");
              }
              if (!herramientaDB.existeEntidad(compra.getCodigoProyeccion(),
                      PeticionesAdminCine.OBTENER_PROYECCION_POR_CODIGO.get())) {
                     throw new EntityNotFoundException(
                             "No existe usuario con el id: " + compra.getIdUsuario() + " en el sistema");
              }
       }

       /**
        * 
        * @param request
        * @return
        * @throws UserDataInvalidException 
        */
       private CompraBoletos extraerDatos(CompraBoletosRequest request) throws UserDataInvalidException {
              CompraBoletos compra = new CompraBoletos();
              compra.setIdUsuario(request.getIdUsuario());
              compra.setCodigoProyeccion(request.getCodigoProyeccion());
              compra.setFechaCompra(request.getFechaCompra());
              compra.setCantidad(request.getCantidad());

              if (!compra.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return compra;
       }

       public List<CompraBoletosResponse> obtenerBoletosComprados(String idUsuario, int inicio) throws DataBaseException, EntityNotFoundException {
              CompraBoletosDB compraBoletosDB = new CompraBoletosDB();
              int fin = inicio + RangoBusquedaElemento.MIS_COMPRAS.getRango();
              List<CompraBoletos> compras = compraBoletosDB.obtenerMisBoletosPorRango(idUsuario, inicio, fin);

              return contruirResponse(compras);

       }

       private List<CompraBoletosResponse> contruirResponse(List<CompraBoletos> boletos) throws EntityNotFoundException, DataBaseException {
              CRUDProyecciones crud = new CRUDProyecciones();
              List<CompraBoletosResponse> responses = new ArrayList<>();
              for (int i = 0; i < boletos.size(); i++) {
                     ProyeccionResponse proyeccionResponse = crud.obtenerProyeccion(boletos.get(i).getCodigoProyeccion());
                     CompraBoletosResponse response = new CompraBoletosResponse();
                     response.setIdUsuario(boletos.get(i).getIdUsuario());
                     response.setCantidad(boletos.get(i).getCantidad());
                     response.setCostoTotal(boletos.get(i).getCostoTotal());
                     response.setFechaCompra(boletos.get(i).getFechaCompra());
                     response.setProyeccion(proyeccionResponse);
                     responses.add(response);
              }
              return responses;
       }
}
