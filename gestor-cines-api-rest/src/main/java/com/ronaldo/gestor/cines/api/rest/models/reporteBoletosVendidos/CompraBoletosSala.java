package com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos;

import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CompraBoletosSala {

       private Sala sala;
       private List<CompraBoleto> compras;
       private double totalRecaudado;

       public String getNombreSala() {
              return sala.getNombre();
       }

       public void setSala(Sala sala) {
              this.sala = sala;
       }

       public List<CompraBoleto> getCompras() {
              return compras;
       }

       public void setCompras(List<CompraBoleto> compras) {
              this.compras = compras;
       }

       public double getTotalRecaudado() {
              return totalRecaudado;
       }

       public void setTotalRecaudado(double totalRecaudado) {
              this.totalRecaudado = totalRecaudado;
       }

       public void calcularTotalRecaudado() {
              for (int i = 0; i < compras.size(); i++) {
                     totalRecaudado += compras.get(i).getCostoTotal();
              }
       }
}
