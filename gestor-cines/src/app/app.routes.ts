import { Routes } from '@angular/router';
import { Login } from '../pages/login/login-page.component';
import { HomeAdminSistema } from '../pages/admin-sistema-page/home-page/home-admin-sistema-page.component ';
import { CinesPage } from '../pages/admin-sistema-page/cines-page/cines-page.component ';
import { CrearCineComponentPage } from '../pages/admin-sistema-page/crear-cine-page/crear-cine-page.component';
import { ActualizarCineComponentPage } from '../pages/admin-sistema-page/actualizar-cine-page/actualizar-cine-page.component';
import { AdminsCinePage } from '../pages/admin-sistema-page/admins-cine-page/admins-cine-page.component ';
import { CrearAdminCineComponentPage } from '../pages/admin-sistema-page/crear-admin-cine-page/crear-admin-cine-page.component';
import { AnunciantesPageComponent } from '../pages/admin-sistema-page/anunciantes-page/anunciantes-page.component';
import { CrearAnunciantePageComponent } from '../pages/admin-sistema-page/crear-anunciante-page/crear-anunciante-page.component';
import { ConfigPreciosAnunciosPage } from '../pages/admin-sistema-page/configuracion-precios-anuncios-page/config-precios-anuncios-page.component';
import { ActualizarPreciosAnunciosPageComponent } from '../pages/admin-sistema-page/actualizar-precios-page/actualizar-precios-page.component';
import { ConfigPeliculasPage } from '../pages/admin-sistema-page/config-peliculas-page/config-peliculas-page.component';
import { CategoriasPage } from '../pages/admin-sistema-page/categorias-page/categorias-page.componet';
import { CategoriaFormPage } from '../pages/admin-sistema-page/categorias-form-page/categorias-form-page.component';
import { CrearPeliculaPage } from '../pages/admin-sistema-page/crear-pelicula-page/crear-pelicula-page.component';
import { ActualizarPeliculaComponentPage } from '../pages/admin-sistema-page/actualizar-pelicula-page/actualizar-pelicula-page.component';
import { CostoFuncionamientoPage } from '../pages/admin-sistema-page/costo-funcionamiento-page/costo-funcionamiento-page-component';
import { CrearCostoCinePage } from '../pages/admin-sistema-page/crear-costo-cine-page/crear-costo-cine-page.component';
import { CostoGlobalPage } from '../pages/admin-sistema-page/costo-global-page/costo-global-page.component';
import { AdminsSistemaPage } from '../pages/admin-sistema-page/admins-sistema-page/admin-sistema-page.component';
import { CrearAdminSistemaComponentPage } from '../pages/admin-sistema-page/crear-admin-sistema-page/crear-admin-sistema-page.component';
import { HomeAdminCine } from '../pages/admin-cine-page/home-page/home-admin-cine-page.component';
import { SalasPageComponent } from '../pages/admin-cine-page/salas-page/salas-page.component';
import { CrearSalaComponentPage } from '../pages/admin-cine-page/crear-sala-page/crear-sala-page.component';
import { ActualizarSalaComponentPage } from '../pages/admin-cine-page/actualizar-sala-page/actualizar-sala-page.component';
import { PeliculasPageComponent } from '../pages/admin-cine-page/peliculas-page/peliculas-page.component';
import { ProyeccionessPageComponent } from '../pages/admin-cine-page/proyeccion-page/proyeccion-page.component';
import { CrearProyeccionComponentPage } from '../pages/admin-cine-page/crear-proyeccion/crear-proyeccion-page.component';
import { ActualizarProyeccionComponentPage } from '../pages/admin-cine-page/actualizar-proyeccion-page/actualizar-proyeccion-page.component';
import { MisAnunciosPageComponent } from '../pages/anunciante-page/mis-anuncios-page/mis-anuncios-page.component';
import { CrearAnunciosComponentPage } from '../pages/anunciante-page/crear-anuncios/crear-anuncios-page.component';
import { MiSaldoPageComponent } from '../pages/general/mi-saldo-page/mi-saldo-page.component';
import { SaldoCinePage } from '../pages/admin-cine-page/saldo-cine-page/saldo-cine-page';
import { RegistroPage } from '../pages/general/registro-page/registro-page';
import { RoleGuardService } from '../services/seguridad/role-guard.services';
import { Roles } from '../shared/user/user-roles';
import { ActualizacionCuentaPageComponent } from '../pages/general/actualizacion-cuenta-page/actualizacion-cuenta-page.component';
import { HomeUsuarioNormalPage } from '../pages/general/home-usuario-normal-page/home-usuario-normal-page.component';
import { ProyeccionesPeliculaPageComponent } from '../pages/general/proyecciones-page/proyecciones-page';
import { CinesDisponiblesPage } from '../pages/general/cines-page/cines-page.component ';
import { CompraBoletosPageComponent } from '../pages/general/comprar-boletos-page/compra-boletos-page.component';
import { MisComprasPage } from '../pages/general/mis-compras-page/mis-compras-page';
import { PagoBloqueoAnunciosPage } from '../pages/admin-cine-page/pago-bloqueo-anuncios-page/pago-bloqueo-anuncios-page';
import { OpinionPage } from '../pages/general/opinion-page/opinion-page';
import { OpinionesPage } from '../pages/general/opiniones-page/opiniones-page';
import { ReportesHomePage } from '../pages/admin-cine-page/reportes-page/reportes-home-page/reportes-home-page';
import { ReporteComentariosSalasPage } from '../pages/admin-cine-page/reportes-page/reporte-comentarios-salas/reporte-comentarios-salas-page';
export const routes: Routes = [
    {
        path: '',
        component: Login
    },
    {
        path: 'home/admin-sistema',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: HomeAdminSistema
    },
    {
        path: 'cines',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CinesPage
    },
    {
        path: 'cine/nuevo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CrearCineComponentPage
    },
    {
        path: 'cine/actualizar/:codigoCine',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: ActualizarCineComponentPage
    },
    {
        path: 'cine/admins/:codigoCine',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: AdminsCinePage
    },
    {
        path: 'adminCine/nuevo/:codigoCine',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CrearAdminCineComponentPage
    },
    {
        path: 'anunciantes',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: AnunciantesPageComponent
    },
    {
        path: 'anunciantes/nuevo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CrearAnunciantePageComponent
    },
    {
        path: 'preciosAnuncios',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: ConfigPreciosAnunciosPage
    },
    {
        path: 'preciosCines/actualizar/:id',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: ActualizarPreciosAnunciosPageComponent
    },
    {
        path: 'peliculas/config',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: ConfigPeliculasPage
    },
    {
        path: 'categorias',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CategoriasPage
    }, {
        path: 'categorias/nuevo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CategoriaFormPage
    },
    {
        path: 'peliculas/nueva',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CrearPeliculaPage
    },
    {
        path: 'peliculas/actualizar/:codigoPelicula',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: ActualizarPeliculaComponentPage
    },
    {
        path: 'costosFuncinamiento',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CostoFuncionamientoPage
    },
    {
        path: 'costosFuncionamiento/nuevo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CrearCostoCinePage
    },
    {
        path: 'costoGlobal',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CostoGlobalPage
    },
    {
        path: 'adminsSistema',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: AdminsSistemaPage
    },
    {
        path: 'adminSistema/nuevo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_SISTEMA },
        component: CrearAdminSistemaComponentPage
    },
    {
        path: 'home/admin-cine',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: HomeAdminCine
    },
    {
        path: 'salas',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: SalasPageComponent
    },
    {
        path: 'salas/nuevo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: CrearSalaComponentPage
    },
    {
        path: 'salas/actualizar/:codigoSala',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: ActualizarSalaComponentPage
    },
    {
        path: 'peliculas',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: PeliculasPageComponent
    },
    {
        path: 'proyecciones',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: ProyeccionessPageComponent
    },
    {
        path: 'proyecciones/nueva',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: CrearProyeccionComponentPage
    },
    {
        path: 'proyecciones/actualizar/:codigoProyeccion',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: ActualizarProyeccionComponentPage
    },
    {
        path: 'misAnuncios',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: MisAnunciosPageComponent
    },
    {
        path: 'misAnuncios/nuevo/:tipo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: CrearAnunciosComponentPage
    },
    {
        path: 'miSaldo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: MiSaldoPageComponent
    },
    {
        path: 'saldo-cine',
        canActivate: [RoleGuardService],
        data: { allowedRoles: Roles.ADMIN_CINE },
        component: SaldoCinePage
    },
    {
        path: 'registro',
        component: RegistroPage
    },
    {
        path: 'cuenta/actualizacion',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: ActualizacionCuentaPageComponent
    },
    {
        path: 'home/usuario-normal',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: HomeUsuarioNormalPage
    }, {
        path: 'proyecciones-pelicula/:codigo-pelicula/:nombre-pelicula',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: ProyeccionesPeliculaPageComponent
    },
    {
        path: 'cines/disponibles',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: CinesDisponiblesPage
    },
    {
        path: 'proyecciones-de-pelicula/compra-boletos/:codigo-proyeccion',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: CompraBoletosPageComponent
    },
    {
        path: 'boletos/mis-boletos',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: MisComprasPage
    },
    {
        path: 'anuncios/bloqueo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE] },
        component: PagoBloqueoAnunciosPage
    },
    {
        path: 'boletos/mis-boletos/opiniones/:tipo/:titulo/:codigo',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: OpinionPage

    },
    {
        path: 'proyecciones-pelicula/opiniones/:codigo/:nombre/:es-pelicula/:codigo-pelicula/:nombre-pelicula',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE, Roles.ADMIN_SISTEMA, Roles.USUARIO_NORMAL] },
        component: OpinionesPage

    },
    {
        path: 'reportes-admin-cine',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE] },
        component: ReportesHomePage
    },
    {
        path: 'reportes-admin-cine/comentarios-de-salas',
        canActivate: [RoleGuardService],
        data: { allowedRoles: [Roles.ADMIN_CINE] },
        component: ReporteComentariosSalasPage
    }
];
