package com.ronaldo.gestor.cines.api.rest.dtos.credenciales;

/**
 *
 * @author ronaldo
 */
public class PropiedadesUsuario {

       private String id;
       private String role;
       private boolean esAnunciante;
       private String codigoCine;

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }

       public String getRole() {
              return role;
       }

       public void setRole(String role) {
              this.role = role;
       }

       public boolean isEsAnunciante() {
              return esAnunciante;
       }

       public void setEsAnunciante(boolean esAnunciante) {
              this.esAnunciante = esAnunciante;
       }

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

}
