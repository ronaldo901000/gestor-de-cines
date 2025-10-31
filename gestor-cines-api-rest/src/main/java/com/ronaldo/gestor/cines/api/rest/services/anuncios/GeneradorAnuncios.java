/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.services.anuncios;

import com.ronaldo.gestor.cines.api.rest.db.anuncios.AnunciosDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import java.util.List;

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
}
