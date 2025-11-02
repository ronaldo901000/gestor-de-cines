import { Usuario } from "../usuario/usuario";

export interface OpinionResponse {
    codigoEntidad: string,
    usuario: Usuario,
    comentario: string,
    calificacion: number,
    fecha: Date,
}