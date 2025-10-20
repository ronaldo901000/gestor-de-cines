import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { Usuario } from '../../../models/usuario/usuario';
import { AdminSistemaServices } from '../../../services/admin-sistema/admin-sistema.service';

@Component({
    selector: 'app-admins-sistema-table-component',
    imports: [ToastComponent],
    templateUrl: './admins-sistema-table.component.html',
})
export class AdminsSistemaTableComponent {
    @Input()
    admins: Usuario[] = []
    @ViewChild
        ('toast') toast!: ToastComponent;

    constructor(private adminService: AdminSistemaServices) { }

    confirmarEliminacion(idAdmin: string): void {
        const confirmado = confirm('¿Estas seguro de que deseas eliminar este admin?');
        if (confirmado) {
            this.eliminarAdmin(idAdmin);
        }
    }

    eliminarAdmin(idAdmin: string) {
        this.adminService.eliminarAdminSistema(idAdmin).subscribe({
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
                this.toast.mensaje = error.error;

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