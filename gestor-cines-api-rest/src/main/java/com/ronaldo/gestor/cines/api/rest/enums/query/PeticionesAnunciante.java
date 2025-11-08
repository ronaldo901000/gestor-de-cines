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
       CREAR_ANUNCIO_IMAGEN("insert into anuncio (codigo, id_anunciante, titulo, tipo, "
               + "descripcion, fecha_registro, precio, duracion_dias,imagen) values(?, ?, ?, ?, ?, ?, ?, ?, ?)"),
       OBTENER_MIS_ANUNCIOS("select * from anuncio where id_anunciante=?"),
       OBTENER_ANUNCIOS_POR_RANGO("select * from anuncio where activo=1"),
       OBTENER_ANUNCIO_IMAGEN("select * from anuncio where codigo=?"),
       AGREGAR_DIA_ACTIVO_ANUNCIO("update anuncio set dias_activo=dias_activo+1 where codigo=?"),
       DESACTIVAR_ANUNCIO("update anuncio set activo =0 where codigo=?"),
       OBTENER_ANUNCIOS("select * from anuncio"),
       ;
       private String peticion;

       private PeticionesAnunciante(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }

}
