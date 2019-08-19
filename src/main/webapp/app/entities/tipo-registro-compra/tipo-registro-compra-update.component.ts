import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoRegistroCompra, TipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';
import { TipoRegistroCompraService } from './tipo-registro-compra.service';

@Component({
  selector: 'jhi-tipo-registro-compra-update',
  templateUrl: './tipo-registro-compra-update.component.html'
})
export class TipoRegistroCompraUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descripcion: []
  });

  constructor(
    protected tipoRegistroCompraService: TipoRegistroCompraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoRegistroCompra }) => {
      this.updateForm(tipoRegistroCompra);
    });
  }

  updateForm(tipoRegistroCompra: ITipoRegistroCompra) {
    this.editForm.patchValue({
      id: tipoRegistroCompra.id,
      descripcion: tipoRegistroCompra.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoRegistroCompra = this.createFromForm();
    if (tipoRegistroCompra.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoRegistroCompraService.update(tipoRegistroCompra));
    } else {
      this.subscribeToSaveResponse(this.tipoRegistroCompraService.create(tipoRegistroCompra));
    }
  }

  private createFromForm(): ITipoRegistroCompra {
    return {
      ...new TipoRegistroCompra(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoRegistroCompra>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
