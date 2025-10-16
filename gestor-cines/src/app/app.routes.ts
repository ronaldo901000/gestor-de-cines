import { Routes } from '@angular/router';
import { Login } from '../pages/login/login-page.component';
import { HomeAdminSistema } from '../pages/admin-sistema-page/home-page/home-admin-sistema-page.component ';
import { CinesPage } from '../pages/admin-sistema-page/cines-page/cines-page.component ';
import { CrearCineComponentPage } from '../pages/admin-sistema-page/crear-cine-page/crear-cine-page.component';
import { ActualizarCineFormComponent } from '../components/cine/actualizar-cine-form-component/actualizar-cine-form.component';
import { ActualizarCineComponentPage } from '../pages/admin-sistema-page/actualizar-cine-page/actualizar-cine-page.component';
import { AdminsCinePage } from '../pages/admin-sistema-page/admins-cine-page/admins-cine-page.component ';
import { CrearAdminCineComponentPage } from '../pages/admin-sistema-page/crear-admin-cine-page/crear-admin-cine-page.component';

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
        path:'cine/actualizar',
        component:ActualizarCineComponentPage
    },
    {
        path:'cine/admins',
        component:AdminsCinePage
    },
    {
        path: 'adminCine/nuevo',
        component:CrearAdminCineComponentPage
    }
];
