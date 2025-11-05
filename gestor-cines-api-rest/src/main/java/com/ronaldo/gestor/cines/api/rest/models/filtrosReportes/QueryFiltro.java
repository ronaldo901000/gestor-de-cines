package com.ronaldo.gestor.cines.api.rest.models.filtrosReportes;

/**
 *
 * @author ronaldo
 */
public enum QueryFiltro {
       BUSCAR_COMENTARIOS_FILTRO_COMPLETO("select * from opinion_sala o join usuario u on o.id_usuario = u.id join sala s on o.codigo_sala = s.codigo where s.codigo_cine = ? and s.codigo = ? and o.fecha >= ? and o.fecha <= ?"),
       BUSCAR_COMENTARIOS_FILTRO_NULO("select * from opinion_sala o join usuario u on o.id_usuario = u.id join sala s on o.codigo_sala = s.codigo where s.codigo_cine = ?"),
       BUSCAR_COMENTARIOS_FILTRO_SALA("select * from opinion_sala o join usuario u on o.id_usuario = u.id join sala s on o.codigo_sala = s.codigo where s.codigo_cine = ? and s.codigo = ?"),
       BUSCAR_COMENTARIOS_FILTRO_FECHAS("select * from opinion_sala o join usuario u on o.id_usuario = u.id join sala s on o.codigo_sala = s.codigo where s.codigo_cine = ? and o.fecha >= ? and o.fecha <= ?"),
       
       
       ;
       
       
       
       private String query;

       private QueryFiltro(String query) {
              this.query = query;
       }

       public String getQuery() {
              return query;
       }

}
