import { ProyeccionResponse } from "../proyeccion/proyeccion-response";

export interface CompraBoletosResponse {
    idUsuario: string,
    proyeccion: ProyeccionResponse,
    fechaCompra: Date,
    cantidad: number,
    costoTotal: number
}