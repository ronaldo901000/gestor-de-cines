import { AfterViewInit, Component, Input, ViewChild, ElementRef } from '@angular/core';

@Component({
    selector: 'app-toast-component',
    templateUrl: './toast.component.html',
    styleUrls: ['./toast.component.css']
})
export class ToastComponent implements AfterViewInit {

    @Input() titulo: string = 'Mensaje';
    @Input() mensaje: string = '';
    @Input() tipo: 'success' | 'danger' | 'warning' | 'info' = 'success';
    @Input() dato1: string = '';
    @Input() dato2: string = '';
    @Input() dato3: string = '';
    @Input() dato4: string = '';
    @Input() dato5: string = '';


    @ViewChild('toastEl') toastEl!: ElementRef;
    private toastInstance: any;

    ngAfterViewInit() {
        if (this.toastEl) {
            this.toastInstance = new bootstrap.Toast(this.toastEl.nativeElement);
        }
    }

    mostrar() {
        if (this.toastEl && this.toastInstance) {
            this.toastInstance.show();
        }
    }
}

declare var bootstrap: any;
