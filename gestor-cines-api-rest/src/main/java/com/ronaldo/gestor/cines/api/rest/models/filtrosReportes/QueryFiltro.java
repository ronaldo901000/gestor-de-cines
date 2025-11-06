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
       
       //para peliculas proyectadas
       
       BUSCAR_PELICULAS_PROYECTADAS_FILTRO_COMPLETO("select * from proyeccion p join sala s on p.codigo_sala=s.codigo where s.codigo=? and p.fecha >= ? and p.fecha <= ?"),
       BUSCAR_PELICULAS_PROYECTADAS_FILTRO_SALA("select * from proyeccion p join sala s on p.codigo_sala=s.codigo where s.codigo=?"),
       
       ;
       
       
       
       private String query;

       private QueryFiltro(String query) {
              this.query = query;
       }

       public String getQuery() {
              return query;
       }

}
