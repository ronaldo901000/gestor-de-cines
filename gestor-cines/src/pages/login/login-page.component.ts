import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserProperties } from '../../shared/user/user-properties';

@Component({
  selector: 'app-login-page',
  imports: [],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class Login implements OnInit{

  alreadyLoggedIn!: boolean;
  currentRole!: string | null;
    idUsuario:string='USR001';
    constructor(private router: Router) {

  }
  ngOnInit(): void {
    this.alreadyLoggedIn = localStorage.getItem('role') != null;
    this.currentRole = localStorage.getItem('role');
  }

  login(role: string) {
    localStorage.setItem('role', role);
    localStorage.setItem(UserProperties.ID,this.idUsuario)
        switch (role) {
      case 'ADMIN-SISTEMA':
        this.router.navigate(['/home/admin-sistema']);
        break;
      case 'ADMIN-CINE':
        this.router.navigate(['/home/admin-cine']);
    }
  }
}