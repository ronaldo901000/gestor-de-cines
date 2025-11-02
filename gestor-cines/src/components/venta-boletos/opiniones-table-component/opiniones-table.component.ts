import { Component, Input } from '@angular/core';
import { OpinionResponse } from '../../../models/opinion/opinion-response';
import { ToastComponent } from "../../toast/toast.component";
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-opiniones-table-component',
  imports: [ToastComponent, DatePipe],
  templateUrl: './opiniones-table.component.html',
})
export class OpinionesTableComponent {
  @Input({required:true})
  opiniones:OpinionResponse[]=[];
}
