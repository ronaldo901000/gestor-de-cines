package com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes;

import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AnuncianteReport {

       private Usuario usuario;
       private List<AnuncioResponse> anuncios;

       public Usuario getUsuario() {
              return usuario;
       }

       public void setUsuario(Usuario usuario) {
              this.usuario = usuario;
       }

       public List<AnuncioResponse> getAnuncios() {
              return anuncios;
       }

       public void setAnuncios(List<AnuncioResponse> anuncios) {
              this.anuncios = anuncios;
       }

       public double getTotalIngresos() {
              double ingresos = 0;
              for (int i = 0; i < anuncios.size(); i++) {
                     ingresos += anuncios.get(i).getPrecio()*anuncios.get(i).getDiasDuracion();
              }
              return ingresos;
       }

       public String getIdUsuario() {
              return usuario.getId();
       }

       public String getNombreUsuario() {
              return usuario.getNombre();
       }

}
