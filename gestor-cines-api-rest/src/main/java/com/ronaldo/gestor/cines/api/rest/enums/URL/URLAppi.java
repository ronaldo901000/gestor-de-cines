package com.ronaldo.gestor.cines.api.rest.enums.URL;

/**
 *
 * @author ronaldo
 */
public enum URLAppi {

       URL("http://localhost:8080/gestor-cines-api-rest/api/v1/");
       private String url;

       private URLAppi(String url) {
              this.url = url;
       }

       public String getUrl() {
              return url;
       }

}
