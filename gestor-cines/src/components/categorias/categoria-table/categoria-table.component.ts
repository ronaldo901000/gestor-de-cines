import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { Usuario } from '../../../models/usuario/usuario';
import { AnuncianteServices } from '../../../services/anunciante/anunciante.services';
import { Status } from '../../../shared/status/status';
import { Categoria } from '../../../models/categoria/categoria';
import { CategoriaService } from '../../../services/categoria/categoria.service';
import { CategoriaResponse } from '../../../models/categoria/categoria-response';

@Component({
    selector: 'app-categoria-table-component',
    imports: [ToastComponent],
    templateUrl: './categoria-table.component.html',
})
export class AnunciantesTableComponent implements OnInit {

    @ViewChild
        ('toast') toast!: ToastComponent;


    categorias: CategoriaResponse[] = []

    constructor(private categoriasServices: CategoriaService) {
    }

    ngOnInit(): void {
        this.traerAnunciantes();
    }

    traerAnunciantes() {
        this.categoriasServices.traerCategorias().subscribe({
            next: (caetegoriasServer: CategoriaResponse[]) => {
                this.categorias = caetegoriasServer;
            },
            error: (error) => {
                this.toast.titulo = 'Error al obtener Categorias';
                this.toast.tipo = 'danger';
                if (error.status == Status.INTERNAL_SERVER_ERROR) {
                    this.toast.mensaje = 'Error al obtener Categorias en la base de datos';
                }
                else {
                    this.toast.mensaje = 'Ocurrio un error desconocido';
                }
                this.toast.mostrar();
            }
        })
    }

}