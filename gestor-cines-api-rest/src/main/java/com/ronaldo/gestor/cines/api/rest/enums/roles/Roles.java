package com.ronaldo.gestor.cines.api.rest.enums.roles;

/**
 *
 * @author ronaldo
 */
public enum Roles {
       ADMIN_SISTEMA("ADMIN-SISTEMA"),
       ADMIN_CINE("ADMIN-CINE"),
       USUARIO_NORMAL("USUARIO-NORMAL");
       private String rol;

       private Roles(String rol) {
              this.rol = rol;
       }

       public String getRol() {
              return rol;
       }

}
