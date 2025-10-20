import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../toast/toast.component';
import { Usuario } from '../../models/usuario/usuario';
import { Cine } from '../../models/cine/cine';
import { AdminCinesServices } from '../../services/admin-cine/admin-cine.services';
import { Status } from '../../shared/status/status';

@Component({
    selector: 'app-tabla-admins-cine-component',
    imports: [ToastComponent],
    templateUrl: './tabla-admins-cine.component.html',
})
export class TablaAdminsCine implements OnInit {
    @Input()
    cine!: Cine;

    @ViewChild
        ('toast') toast!: ToastComponent;


    admins: Usuario[] = []

    constructor(private adminsService: AdminCinesServices) {
    }

    ngOnInit(): void {
        this.traerAdmins();
    }

    traerAdmins() {
        this.adminsService.traerAdminsCine(this.cine.codigo).subscribe({
            next: (admins: Usuario[]) => {
                this.admins = admins;
            },
            error: (error) => {
                console.error('Error al cargar cines:', error)
            }
        })
    }

    confirmarEliminacion(idAdmin: string): void {
        const confirmado = confirm('¿Estas seguro de que deseas eliminar este admin?');
        if (confirmado) {
            this.eliminarAdmin(idAdmin);
        }
    }

    eliminarAdmin(idAdmin: string) {
        this.adminsService.eliminarAdminCine(idAdmin).subscribe({
            next: () => {
                this.toast.titulo = 'Eliminacion exitosa';
                this.toast.tipo = 'warning';
                this.toast.mensaje = 'Eliminacion realizada con exito';
                this.toast.mostrar();
                this.eliminarDeLista(idAdmin);
            },
            error: (error) => {
                this.toast.titulo = 'Error al Eliminar';
                this.toast.tipo = 'danger';
                if (error.status === Status.INTERNAL_SERVER_ERROR) {
                    this.toast.mensaje = 'Error en la base de datos al eliminar admin';
                } else if (error.status === Status.NOT_FOUND) {
                    this.toast.mensaje = 'No se encontro el usuario en la base de datos';
                } else if (error.status === Status.BAD_REQUEST) {
                    this.toast.mensaje = 'Error en los datos enviados, verifica';
                } else {
                    this.toast.mensaje = 'Sucedio un error desconocido';
                }
                this.toast.mostrar();
            }
        });
    }

    eliminarDeLista(idAdmin: String) {
        for (let i = 0; i < this.admins.length; i++) {
            if (this.admins[i].id === idAdmin) {
                this.admins.splice(i, 1);
                break;
            }
        }
    }

}