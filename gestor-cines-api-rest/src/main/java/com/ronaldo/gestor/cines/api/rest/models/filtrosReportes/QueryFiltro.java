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
       //para 5 salas mas gustadas
       BUSCAR_5_SALAS_GUSTADAS_FILTRO_COMPLETO("select * from opinion_sala where codigo_sala=? and fecha >= ? and fecha <= ?"),
       BUSCAR_5_SALAS_GUSTADAS_FILTRO_SALA("select * from opinion_sala where codigo_sala=?"),
       
       //para obtener boletos vendidos
       BUSCAR_COMPRA_BOLETOS_VENDIDOS_FILTRO_COMPLETO("select * from compra_boletos c join proyeccion p on c.codigo_proyeccion=p.codigo where p.codigo_sala=? and c.fecha_compra >= ? and c.fecha_compra <= ?"),
       BUSCAR_COMPRA_BOLETOS_VENDIDOS_FILTRO_SALA("select * from compra_boletos c join proyeccion p on c.codigo_proyeccion=p.codigo where p.codigo_sala=?"),
       
       ;
       
       private String query;

       private QueryFiltro(String query) {
              this.query = query;
       }

       public String getQuery() {
              return query;
       }

}
