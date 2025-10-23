import { Time } from "@angular/common";
import { Pelicula } from "../pelicula/pelicula";
import { SalaResponse } from "../sala/sala-response";

export interface ProyeccionResponse{
    codigo:string,
    pelicula:Pelicula,
    sala:SalaResponse,
    fecha:Date,
    horaInicio:Time,
    horaFin:Time,
    precio:number
}