import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'duracion'
})
export class DuracionPipe implements PipeTransform {


    transform(minutos: number): string {
        if (!minutos || minutos <= 0) return '0m';
        const horas = Math.floor(minutos / 60);
        const mins = minutos % 60;
        return `${horas}h ${mins}m`;
    }
    
}
