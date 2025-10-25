package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum PeticionesAnunciante {
       
       BUSCAR_ANUNCIO("select * from anuncio where codigo=?"),
       CREAR_ANUNCIO_TEXTO("insert into anuncio (codigo, id_anunciante, titulo, tipo, "
               + "descripcion, fecha_registro, precio, duracion_dias) values(?, ?, ?, ?, ?, ?, ?, ?)"),
       OBTENER_COSTO_ANUNCIO("select * from precio_anuncio where id=?"),
       CREAR_ANUNCIO_VIDEO("insert into anuncio (codigo, id_anunciante, titulo, tipo, "
               + "descripcion, fecha_registro, precio, duracion_dias,link_video) values(?, ?, ?, ?, ?, ?, ?, ?, ?)"),
       ;
       private String peticion;

       private PeticionesAnunciante(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }

}
