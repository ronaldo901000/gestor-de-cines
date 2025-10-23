import { Time } from "@angular/common";

export interface Proyeccion {
    codigo: string,
    codigoPelicula: string,
    codigoSala: string,
    fecha: Date,
    horaInicio: Time,
    horaFin: Time,
    precio: number
}