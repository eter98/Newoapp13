import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMiembros } from 'app/shared/model/miembros.model';

@Component({
  selector: 'jhi-miembros-detail',
  templateUrl: './miembros-detail.component.html'
})
export class MiembrosDetailComponent implements OnInit {
  miembros: IMiembros;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ miembros }) => {
      this.miembros = miembros;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
