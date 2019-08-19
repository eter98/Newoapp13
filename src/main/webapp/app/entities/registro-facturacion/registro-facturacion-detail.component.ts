import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistroFacturacion } from 'app/shared/model/registro-facturacion.model';

@Component({
  selector: 'jhi-registro-facturacion-detail',
  templateUrl: './registro-facturacion-detail.component.html'
})
export class RegistroFacturacionDetailComponent implements OnInit {
  registroFacturacion: IRegistroFacturacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ registroFacturacion }) => {
      this.registroFacturacion = registroFacturacion;
    });
  }

  previousState() {
    window.history.back();
  }
}
