/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.services.anuncios;

import com.ronaldo.gestor.cines.api.rest.db.anuncios.AnunciosDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.RangoBusquedaElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class GeneradorAnuncios {

       public List<AnuncioResponse> obtenerAnuncios(String idAnunciante) throws DataBaseException, EntityNotFoundException {
              AnunciosDB anunciosDB = new AnunciosDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              if (!herramientaDB.existeEntidad(idAnunciante, PeticionAdminSistema.OBTENER_UN_ANUNCIANTE.get())) {
                     throw new EntityNotFoundException("El usuario con id: " + idAnunciante + " no es anunciante");
              }
              return anunciosDB.obtenerAnuncios(idAnunciante);
       }

       public List<AnuncioResponse> obtenerAnunciosPorRango(int inicio) throws DataBaseException, UserDataInvalidException {
              int fin;
              try {
                     fin = RangoBusquedaElemento.ANUNCIOS.getRango() + inicio;
              } catch (NumberFormatException e) {
                     throw new UserDataInvalidException("Error en el indice enviado");
              }

              AnunciosDB anunciosDB = new AnunciosDB();
              return anunciosDB.obtenerAnunciosPorRango(inicio, fin);
       }

       public byte[] obtenerImagenAnuncio(String codigoAnuncio) throws EntityNotFoundException, DataBaseException {
              AnunciosDB anunciosDB = new AnunciosDB();
              Optional<byte[]> imagen = anunciosDB.obtenerImagenAnuncio(codigoAnuncio);
              if (imagen.isEmpty()) {
                     throw new EntityNotFoundException("Imagen del anuncio no encontrado en la base de datos");
              }
              return imagen.get();
       }

       public String obtenerLink(String codigoAnuncio) throws DataBaseException, EntityNotFoundException {
              AnunciosDB anunciosDB = new AnunciosDB();
              Optional<String> link=anunciosDB.obtenerLink(codigoAnuncio);
              if(link.isEmpty()){
                     throw new EntityNotFoundException("link del anuncio no encontrado en la base de datos");
              }
              return link.get();
       }

       public List<AnuncioResponse> obtenerTodosLosAnuncios() throws DataBaseException {
              AnunciosDB anunciosDB = new AnunciosDB();
              return anunciosDB.obtenerTodosLosAnuncios();
       }
}
