package com.ronaldo.gestor.cines.api.rest.services.anuncios;

import com.ronaldo.gestor.cines.api.rest.db.anuncios.AnunciosDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioImagenRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioTextoRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioVideoRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAnunciante;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.SaldoInsuficienteException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.Anuncio;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.AnuncioImagen;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.AnuncioTexto;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.AnuncioVideo;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author ronaldo
 */
public class CRUDAnuncios {

       public static final int ID_PRECIO_ANUNCIO_TXT = 1;
       public static final int ID_PRECIO_ANUNCIO_IMAGEN = 2;
       public static final int ID_PRECIO_ANUNCIO_VIDEO = 3;

       /**
        *
        * @param anuncioRequest
        * @throws UserDataInvalidException
        * @throws EntityAlreadyExistsException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws SaldoInsuficienteException
        */
       public void crearAnuncioTexto(AnuncioTextoRequest anuncioRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException, DataBaseException, EntityNotFoundException, SaldoInsuficienteException {
              AnunciosDB anunciosDB = new AnunciosDB();
              AnuncioTexto anuncio = (AnuncioTexto) extraerAnuncioTexto(anuncioRequest);
              Usuario usuario = new Usuario();
              usuario.setId(anuncio.getIdAnunciante());

              //se verifican las disponibilidades en la db y si el usuario cuenta con los creditos necesarios
              verificarExistenciasEnDB(anuncio);
              double costoTotal = anuncio.calcularCantidadApagar(ID_PRECIO_ANUNCIO_TXT);

              verificarSaldoAnunciante(costoTotal, anuncio.getIdAnunciante());

              //se crea el anuncio
              anunciosDB.crearAnuncioTXTOVideo(
                      anuncio,
                      null, 
                      usuario.obtenerNuevoSaldo(costoTotal),
                      PeticionesAnunciante.CREAR_ANUNCIO_TEXTO.get(),
                      0
              );
       }

       /**
        *
        * @param anuncioRequest
        * @throws UserDataInvalidException
        * @throws EntityAlreadyExistsException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws SaldoInsuficienteException
        */
       public void crearAnuncioVideo(AnuncioVideoRequest anuncioRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException, DataBaseException, EntityNotFoundException, SaldoInsuficienteException {
              AnunciosDB anunciosDB = new AnunciosDB();
              Usuario usuario = new Usuario();
              
              AnuncioVideo anuncio = extraerAnuncioVideo(anuncioRequest);
              usuario.setId(anuncio.getIdAnunciante());
              
           
              //se verifican las disponibilidades en la db y si el usuario cuenta con los creditos necesarios
              verificarExistenciasEnDB(anuncio);
              double costoTotal = anuncio.calcularCantidadApagar(ID_PRECIO_ANUNCIO_VIDEO);

              verificarSaldoAnunciante(costoTotal, anuncio.getIdAnunciante());

              //se crea el anuncio
              anunciosDB.crearAnuncioTXTOVideo(
                      anuncio,
                      anuncio.getLinkVideo(),
                      usuario.obtenerNuevoSaldo(costoTotal),
                      PeticionesAnunciante.CREAR_ANUNCIO_VIDEO.get()
                      ,ID_PRECIO_ANUNCIO_VIDEO);
       }

       /**
        *
        * @param anuncio
        * @throws DataBaseException
        * @throws EntityAlreadyExistsException
        * @throws EntityNotFoundException
        */
       private void verificarExistenciasEnDB(Anuncio anuncio) throws DataBaseException, EntityAlreadyExistsException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              if (herramientaDB.existeEntidad(anuncio.getCodigo(), PeticionesAnunciante.BUSCAR_ANUNCIO.get())) {
                     throw new EntityAlreadyExistsException("Codigo de anuncio no disponible, ingresa otro");
              }
              if (!herramientaDB.existeEntidad(anuncio.getIdAnunciante(), PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("El id " + anuncio.getIdAnunciante() + " no pertenece a ningun usuario");
              }
              if (!herramientaDB.existeEntidad(anuncio.getIdAnunciante(), PeticionAdminSistema.OBTENER_UN_ANUNCIANTE.get())) {
                     throw new EntityNotFoundException("El ID " + anuncio.getIdAnunciante() + " no es anunciante");
              }
       }

       /**
        *
        * @param costoTransaccion
        * @param idAnunciante
        * @throws DataBaseException
        * @throws SaldoInsuficienteException
        */
       private void verificarSaldoAnunciante(double costoTransaccion, String idAnunciante) throws DataBaseException, SaldoInsuficienteException {
              Usuario usuario = new Usuario();
              usuario.setId(idAnunciante);
              if (!usuario.cuentaConSaldoSuficiente(costoTransaccion)) {
                     throw new SaldoInsuficienteException(
                             "No cuentas con los creditos suficientes para realizar esta compra, ve a recargar"
                     );
              }
       }

       /**
        *
        * @param request
        * @return
        * @throws UserDataInvalidException
        */
       private AnuncioTexto extraerAnuncioTexto(AnuncioTextoRequest request) throws UserDataInvalidException {
              AnuncioTexto anuncio = new AnuncioTexto();

              anuncio.setCodigo(request.getCodigo());
              anuncio.setIdAnunciante(request.getIdAnunciante());
              anuncio.setTitulo(request.getTitulo());
              anuncio.setTipo(request.getTipo());
              anuncio.setDescripcion(request.getDescripcion());
              anuncio.setFechaRegistro(request.getFechaRegistro());
              anuncio.setPrecio(request.getPrecio());
              anuncio.setDuracion(request.getDuracion());

              if (!anuncio.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return anuncio;
       }

       /**
        *
        * @param request
        * @return
        * @throws UserDataInvalidException
        */
       private AnuncioVideo extraerAnuncioVideo(AnuncioVideoRequest request) throws UserDataInvalidException {
              AnuncioVideo anuncio = new AnuncioVideo();

              anuncio.setCodigo(request.getCodigo());
              anuncio.setIdAnunciante(request.getIdAnunciante());
              anuncio.setTitulo(request.getTitulo());
              anuncio.setTipo(request.getTipo());
              anuncio.setDescripcion(request.getDescripcion());
              anuncio.setFechaRegistro(request.getFechaRegistro());
              anuncio.setPrecio(request.getPrecio());
              anuncio.setDuracion(request.getDuracion());
              anuncio.setLinkVideo(request.getLinkVideo());
              if (!anuncio.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }

              if (!StringUtils.isNotBlank(anuncio.getLinkVideo())) {
                     throw new UserDataInvalidException("Link no enviado");
              }
              return anuncio;
       }

       public AnuncioImagenRequest crearRequestAnuncioImagen(
               String codigo,
               String idAnunciante,
               String titulo,
               String tipo,
               String descripcion,
               String fechaRegistro,
               double precio,
               int duracion,
               InputStream imagenFileStream,
               FormDataContentDisposition fileDetails
       ) throws UserDataInvalidException, IOException {
              AnuncioImagenRequest anuncio = new AnuncioImagenRequest();
              anuncio.setCodigo(codigo);
              anuncio.setIdAnunciante(idAnunciante);
              anuncio.setTitulo(titulo);
              anuncio.setTipo(tipo);
              anuncio.setDescripcion(descripcion);
              if (fechaRegistro == null || fechaRegistro.isBlank()) {
                     throw new UserDataInvalidException("La fecha es obligatoria");
              }
              LocalDate fecha = LocalDate.parse(fechaRegistro);
              anuncio.setFechaRegistro(fecha);
              anuncio.setPrecio(precio);
              anuncio.setDuracion(duracion);
              if (imagenFileStream != null) {
                     byte[] imagenBytes = imagenFileStream.readAllBytes();
                     anuncio.setImagen(imagenBytes);
              }
              return anuncio;
       }

       private AnuncioImagen extraerAuncioImagen(AnuncioImagenRequest request) throws UserDataInvalidException {
              AnuncioImagen anuncio = new AnuncioImagen();
              anuncio.setCodigo(request.getCodigo());
              anuncio.setIdAnunciante(request.getIdAnunciante());
              anuncio.setTitulo(request.getTitulo());
              anuncio.setTipo(request.getTipo());
              anuncio.setDescripcion(request.getDescripcion());
              anuncio.setFechaRegistro(request.getFechaRegistro());
              anuncio.setPrecio(request.getPrecio());
              anuncio.setDuracion(request.getDuracion());
              anuncio.setImagen(request.getImagen());
              if (!anuncio.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }

              if (anuncio.getImagen() == null) {
                     throw new UserDataInvalidException("Imagen no enviada");
              }
              return anuncio;
       }

       public void crearAnuncioImagen(AnuncioImagenRequest anuncioRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException, DataBaseException, EntityNotFoundException, SaldoInsuficienteException {
              AnunciosDB anunciosDB = new AnunciosDB();
              Usuario usuario = new Usuario();

              AnuncioImagen anuncio = extraerAuncioImagen(anuncioRequest);
              usuario.setId(anuncio.getIdAnunciante());
              //se verifican las disponibilidades en la db y si el usuario cuenta con los creditos necesarios
              verificarExistenciasEnDB(anuncio);
              double costoTotal = anuncio.calcularCantidadApagar(ID_PRECIO_ANUNCIO_IMAGEN);

              verificarSaldoAnunciante(costoTotal, anuncio.getIdAnunciante());

              //se crea el anuncio
              anunciosDB.crearAnuncioImagen(anuncio, costoTotal);
       }


}
