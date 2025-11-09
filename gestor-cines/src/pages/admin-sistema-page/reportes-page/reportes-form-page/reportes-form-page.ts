import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderAdminSistemaComponent } from "../../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ReporteGananciasAnuncianteFormComponent } from "../../../../components/reportes-admin-sistema-component/reporte-ganancias-anunciante-form.component/reporte-ganancias-anunciante-form.component";
import { ReporteSalasPopularesFormComponent } from "../../../../components/reportes-admin-sistema-component/reporte-salas-populares-form.component/reporte-salas-populares-form.component";
import { ReporteSalasComentadasFormComponent } from "../../../../components/reportes-admin-sistema-component/reporte-salas-comentadas-form.component/reporte-salas-comentadas-form.component";
import { ReporteGananciasFormComponent } from "../../../../components/reportes-admin-sistema-component/reporte-ganancias-form.component/reporte-ganancias-form.component";
import { ReporteAnunciosCompradosFormComponent } from "../../../../components/reportes-admin-sistema-component/reporte-anuncios-comprados-form.component/reporte-anuncios-comprados-form.component";

@Component({
  selector: 'app-reportes-form-page',
  imports: [HeaderAdminSistemaComponent, RouterLink, ReporteGananciasAnuncianteFormComponent, ReporteSalasPopularesFormComponent, ReporteSalasComentadasFormComponent, ReporteGananciasFormComponent, ReporteAnunciosCompradosFormComponent],
  templateUrl: './reportes-form-page.html',
})
export class ReportesFormPage implements OnInit {
  encabezado!: string;
  tipo!: string;
  constructor(private router: ActivatedRoute) { }

  ngOnInit(): void {
    this.encabezado = this.router.snapshot.params['titulo'];
    this.encabezado = this.encabezado.toUpperCase();
    this.tipo = this.router.snapshot.params['tipo'];
  }
}
