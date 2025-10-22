import { Routes } from '@angular/router';
import { Login } from '../pages/login/login-page.component';
import { HomeAdminSistema } from '../pages/admin-sistema-page/home-page/home-admin-sistema-page.component ';
import { CinesPage } from '../pages/admin-sistema-page/cines-page/cines-page.component ';
import { CrearCineComponentPage } from '../pages/admin-sistema-page/crear-cine-page/crear-cine-page.component';
import { ActualizarCineFormComponent } from '../components/cine/actualizar-cine-form-component/actualizar-cine-form.component';
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

export const routes: Routes = [
    {
        path: '',
        component: Login
    },
    {
        path:'home/admin-sistema',
        component: HomeAdminSistema
    },
    {
        path:'cines',
        component: CinesPage
    },
    {
        path:'cine/nuevo',
        component: CrearCineComponentPage
    },
    {
        path:'cine/actualizar/:codigoCine',
        component:ActualizarCineComponentPage
    },
    {
        path:'cine/admins/:codigoCine',
        component:AdminsCinePage
    },
    {
        path: 'adminCine/nuevo/:codigoCine',
        component:CrearAdminCineComponentPage
    },
    {
        path: 'anunciantes',
        component: AnunciantesPageComponent
    },
    {
        path:'anunciantes/nuevo',
        component: CrearAnunciantePageComponent
    },
    {
        path:'preciosAnuncios',
        component:ConfigPreciosAnunciosPage
    },
    {
        path:'preciosCines/actualizar/:id',
        component: ActualizarPreciosAnunciosPageComponent
    },
    {
        path:'peliculas/config',
        component:ConfigPeliculasPage
    },
    {
        path:'categorias',
        component:CategoriasPage
    },{
        path:'categorias/nuevo',
        component:CategoriaFormPage
    },
    {
        path:'peliculas/nueva',
        component:CrearPeliculaPage
    },
    {
        path: 'peliculas/actualizar/:codigoPelicula',
        component:ActualizarPeliculaComponentPage
    },
    {
        path: 'costosFuncinamiento',
        component:CostoFuncionamientoPage
    },
    {
        path: 'costosFuncionamiento/nuevo',
        component:CrearCostoCinePage
    },
    {
        path: 'costoGlobal',
        component:CostoGlobalPage
    },
    {
        path: 'adminsSistema',
        component:AdminsSistemaPage
    },
    {
        path: 'adminSistema/nuevo',
        component:CrearAdminSistemaComponentPage
    },
    {
        path: 'home/admin-cine',
        component:HomeAdminCine
    },
    {
        path: 'salas',
        component:SalasPageComponent
    },
    {
        path: 'salas/nuevo',
        component:CrearSalaComponentPage
    },
    {
        path: 'salas/actualizar/:codigoSala',
        component:ActualizarSalaComponentPage
    },
    {
        path: 'peliculas',
            component:PeliculasPageComponent
    }
];
