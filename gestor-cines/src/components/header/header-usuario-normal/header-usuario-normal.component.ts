import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { UserProperties } from '../../../shared/user/user-properties';

@Component({
  selector: 'app-header-usuario-normal',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header-usuario-normal.component.html',
  styleUrl: './header-usuario-normal.component.css'
})
export class HeaderUsuarioNormalComponent implements OnInit {

  esAnunciante!: string | null;
  idUsuario!: string | null;

  constructor(private router: Router){

  }
  ngOnInit(): void {
    this.idUsuario = localStorage.getItem(UserProperties.ID);
    this.esAnunciante=localStorage.getItem(UserProperties.ES_ANUNCIANTE);
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/']);
    this.ngOnInit();
  }
}
