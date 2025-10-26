import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { UserProperties } from '../../../shared/user/user-properties';

@Component({
  selector: 'app-header-admin-cine',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header-admin-cine.component.html',
  styleUrl: './header-admin-cine.component.css'
})
export class HeaderAdminCineComponent implements OnInit {

  esAnunciante!: string | null;
  idUsuario!: string | null;
  constructor(private router: Router){

  }
  ngOnInit(): void {
    this.idUsuario = localStorage.getItem(UserProperties.ID);
    this.esAnunciante=localStorage.getItem(UserProperties.ES_ANUNCIANTE)
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/']);
    this.ngOnInit();
  }
}
