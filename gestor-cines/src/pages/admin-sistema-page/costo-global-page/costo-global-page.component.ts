import { Component, OnInit, ViewChild } from "@angular/core";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { RouterLink } from "@angular/router";
import { CurrencyPipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { CostoGlobalServices } from "../../../services/costo-funcionamiento/costo-global.services";
import { ToastComponent } from "../../../components/toast/toast.component";
import { CostoGlobal } from "../../../models/costo-funcionamiento/costo-global";
import { CostoGlobalFormComponent } from "../../../components/costos-funcionamiento/costo-global-form/costos-global-form.component";
@Component({
    selector: 'app-costo-global-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, FormsModule, CostoGlobalFormComponent],
    templateUrl: './costo-global-page.component.html',
})
export class CostoGlobalPage {

    costoActual: CostoGlobal = { costo: 0 };
    @ViewChild
        ('toast') toast!: ToastComponent;


}