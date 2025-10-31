package com.ronaldo.gestor.cines.api.rest.models.anuncios;

/**
 *
 * @author ronaldo
 */
public class AnuncioImagen extends Anuncio {

       private byte[] imagen;

       public byte[] getImagen() {
              return imagen;
       }

       public void setImagen(byte[] imagen) {
              this.imagen = imagen;
       }

}
