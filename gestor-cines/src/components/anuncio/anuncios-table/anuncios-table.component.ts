import { Component, Input } from "@angular/core";
import { AnuncioResponse } from "../../../models/anuncio/anuncio-response";
import { DatePipe, CurrencyPipe } from "@angular/common";

@Component({
    selector: 'app-anuncios-table-component',
    imports: [DatePipe,CurrencyPipe],
    templateUrl: './anuncios-table.component.html',

})
export class AnunciosTableComponent {
    @Input({required:true})
    anuncios:AnuncioResponse []=[];
}