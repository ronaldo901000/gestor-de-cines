import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { UserProperties } from '../../../shared/user/user-properties';

@Component({
  selector: 'app-header-admin-cine',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header-admin-cine.component.html',
  styleUrl: './header-admin-cine.component.css'
})
export class HeaderAdminCineComponent implements OnInit {

  esAnunciante: boolean = true;
  idUsuario!: string | null;

  ngOnInit(): void {
    this.idUsuario=localStorage.getItem(UserProperties.ID);
  }
}
