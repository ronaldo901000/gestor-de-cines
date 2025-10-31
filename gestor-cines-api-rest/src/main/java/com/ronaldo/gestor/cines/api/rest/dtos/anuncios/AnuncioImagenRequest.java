package com.ronaldo.gestor.cines.api.rest.dtos.anuncios;

/**
 *
 * @author ronaldo
 */
public class AnuncioImagenRequest extends AnunciosRequest {

       private byte[] imagen;

       public byte[] getImagen() {
              return imagen;
       }

       public void setImagen(byte[] imagen) {
              this.imagen = imagen;
       }

}
