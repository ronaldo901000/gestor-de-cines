package com.ronaldo.gestor.cines.api.rest.dtos.usuarios;

import com.ronaldo.gestor.cines.api.rest.dtos.anunciantes.AnuncianteRequest;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class Anunciante implements Editable {

       private String idUsuario;

       public Anunciante(AnuncianteRequest anuncianteRequest) {
              this.idUsuario = anuncianteRequest.getIdUsuario();
       }

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public boolean idValido() {
              idUsuario = idUsuario.trim();
              return StringUtils.isNotBlank(idUsuario);
       }

}
