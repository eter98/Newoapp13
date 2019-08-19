import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';

@Component({
  selector: 'jhi-tipo-registro-compra-detail',
  templateUrl: './tipo-registro-compra-detail.component.html'
})
export class TipoRegistroCompraDetailComponent implements OnInit {
  tipoRegistroCompra: ITipoRegistroCompra;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoRegistroCompra }) => {
      this.tipoRegistroCompra = tipoRegistroCompra;
    });
  }

  previousState() {
    window.history.back();
  }
}
