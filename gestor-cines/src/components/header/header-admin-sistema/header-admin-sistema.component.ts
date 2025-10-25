import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { UserProperties } from '../../../shared/user/user-properties';

@Component({
  selector: 'app-header-admin-sistema',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header-admin-sistema.component.html',
  styleUrl: './header-admin-sistema.component.css'
})
export class HeaderAdminSistemaComponent implements OnInit{

    esAnunciante: boolean = true;
    idUsuario!: string | null;
  
    ngOnInit(): void {
      this.idUsuario=localStorage.getItem(UserProperties.ID);
    }
}
