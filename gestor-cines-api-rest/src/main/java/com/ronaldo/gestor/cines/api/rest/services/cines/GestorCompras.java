package com.ronaldo.gestor.cines.api.rest.services.cines;

import com.ronaldo.gestor.cines.api.rest.db.adminCine.AdminCineDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.pagoBloqueoAnuncio.PagoBloqueoRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.SaldoInsuficienteException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.pagosBloqueo.PagoBloqueo;
import com.ronaldo.gestor.cines.api.rest.services.preciosAnuncios.CRUDPreciosAnuncios;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class GestorCompras {
       
       /**
        * 
        * @param request
        * @throws UserDataInvalidException
        * @throws EntityNotFoundException
        * @throws DataBaseException
        * @throws SaldoInsuficienteException 
        */
       public void comprarBloqueoAnuncios(PagoBloqueoRequest request) throws UserDataInvalidException,
               EntityNotFoundException, DataBaseException, SaldoInsuficienteException {
              CRUDPreciosAnuncios crud = new CRUDPreciosAnuncios();
              AdminCineDB adminCineDB = new AdminCineDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              PagoBloqueo pago = extraer(request);

              if (!herramientaDB.existeEntidad(pago.getCodigoCine(), PeticionAdminSistema.OBTENER_CINE_POR_CODIGO.get())) {
                     throw new EntityNotFoundException("Cine no registrado en el sistema");
              }
              //se calcula el precio a pagar
              pago.calcularCostoTotal(crud.obtenerCostoBloqueo());

              //se obtiene el saldo actual del cine
              CarteraCineServices cartera = new CarteraCineServices();
              
              if (!pago.haySaldoSuficiente(cartera.obtenerSaldoActual(pago.getCodigoCine()))) {
                     throw new SaldoInsuficienteException(
                             "El cine no cuenta con el saldo suficiente para hacer esta compra"
                     );
              }

              //se hace la transaccion
              adminCineDB.comprarBloqueoAnuncio(pago);
              
       }

       /**
        * 
        * @param request
        * @return
        * @throws UserDataInvalidException 
        */
       private PagoBloqueo extraer(PagoBloqueoRequest request) throws UserDataInvalidException {
              PagoBloqueo pago = new PagoBloqueo();

              pago.setCodigoCine(request.getCodigoCine());
              pago.setFechaPago(request.getFechaPago());
              pago.setTotalDias(request.getTotalDias());

              if (!pago.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return pago;
       }

       /**
        * 
        * @param codigoCine
        * @return
        * @throws DataBaseException
        * @throws UserDataInvalidException 
        */
       public boolean tieneBloqueadorDeAnuncios(String codigoCine) throws DataBaseException, UserDataInvalidException {
              if(!StringUtils.isNotBlank(codigoCine)){
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              AdminCineDB adminCineDB = new AdminCineDB();
              return adminCineDB.tieneBloqueadorAnuncios(codigoCine);
       }

}
