import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../toast/toast.component';
import { Usuario } from '../../models/usuario/usuario';
import { Cine } from '../../models/cine/cine';
import { AdminCinesServices } from '../../services/admin-cine/admin-cine.services';
import { AdminCine } from '../../models/admin-cine/admin-cine';

@Component({
    selector: 'app-tabla-admins-cine-component',
    imports: [],
    templateUrl: './tabla-admins-cine.component.html',
})
export class TablaAdminsCine implements OnInit {
   @Input()
    cine!:Cine;
   
    @ViewChild
    ('toast') toast!: ToastComponent;

    
    admins:Usuario[]=[]
    
    constructor(private adminsService:AdminCinesServices) {
    }

    ngOnInit(): void {
        this.traerAdmins();
    }

    traerAdmins(){
        this.adminsService.traerAdminsCine(this.cine.codigo).subscribe({
            next:(admins:Usuario[])=>{
                console.log('total admins: '+admins.length)
                this.admins=admins;
            },
            error:(error)=>{
                 console.error('Error al cargar cines:', error)
            }
        })
    }
    eliminarAdmin(idAdmin:string){
        console.log('se va a eliminar admin cine');

    }

}