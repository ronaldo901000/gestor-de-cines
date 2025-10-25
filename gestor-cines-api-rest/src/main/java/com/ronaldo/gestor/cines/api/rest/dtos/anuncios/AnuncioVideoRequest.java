package com.ronaldo.gestor.cines.api.rest.dtos.anuncios;

/**
 *
 * @author ronaldo
 */
public class AnuncioVideoRequest extends AnunciosRequest {

       private String linkVideo;

       public String getLinkVideo() {
              return linkVideo;
       }

       public void setLinkVideo(String linkVideo) {
              this.linkVideo = linkVideo;
       }

}
