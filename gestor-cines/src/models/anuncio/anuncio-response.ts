export interface AnuncioResponse {
    codigo: string,
    idAnunciante: string,
    titulo: string,
    tipo: string,
    descripcion: string,
    fechaRegistro: Date,
    precio: number,
    diasDuracion: number,
    diasActivo: number,
    activo: boolean,
}