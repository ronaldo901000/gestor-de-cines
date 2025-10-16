import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { Usuario } from '../../../models/usuario/usuario';
import { AnuncianteServices } from '../../../services/anunciante/anunciante.services';
import { Anunciante } from '../../../models/anunciante/anunciante';
import { Status } from '../../../shared/status/status';

@Component({
    selector: 'app-anunciantes-table-component',
    imports: [ToastComponent],
    templateUrl: './anunciante-table.component.html',
})
export class AnunciantesTableComponent implements OnInit {

    @ViewChild
        ('toast') toast!: ToastComponent;


    anunciantes: Usuario[] = []

    constructor(private anuncianteService: AnuncianteServices) {
    }

    ngOnInit(): void {
        this.traerAnunciantes();
    }

    traerAnunciantes() {
        this.anuncianteService.traerAnunciantes().subscribe({
            next: (anunciantesServer: Usuario[]) => {
                this.anunciantes = anunciantesServer;
            },
            error: (error) => {
                this.toast.titulo = 'Error al obtener anunciantes';
                this.toast.tipo = 'danger';
                if (error.status == Status.INTERNAL_SERVER_ERROR) {
                    this.toast.mensaje = 'Error al obtener anunciantes en la base de datos';
                }
                else {
                    this.toast.mensaje = 'Ocurrio un error desconocido';
                }
                this.toast.mostrar();
            }
        })
    }
    eliminarAnunciante(idAdmin: string) {
    }

    eliminarDeLista(idAdmin: String) {
        for (let i = 0; i < this.anunciantes.length; i++) {
            if (this.anunciantes[i].id === idAdmin) {
                this.anunciantes.splice(i, 1);
                break;
            }
        }
    }

}