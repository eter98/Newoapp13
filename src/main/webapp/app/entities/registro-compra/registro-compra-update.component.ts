import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRegistroCompra, RegistroCompra } from 'app/shared/model/registro-compra.model';
import { RegistroCompraService } from './registro-compra.service';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';
import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';
import { TipoRegistroCompraService } from 'app/entities/tipo-registro-compra';

@Component({
  selector: 'jhi-registro-compra-update',
  templateUrl: './registro-compra-update.component.html'
})
export class RegistroCompraUpdateComponent implements OnInit {
  isSaving: boolean;

  miembros: IMiembros[];

  tiporegistrocompras: ITipoRegistroCompra[];

  editForm = this.fb.group({
    id: [],
    valor: [],
    fechaRegistro: [],
    idTransaccion: [],
    miembro: [],
    tipoRegistro: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected registroCompraService: RegistroCompraService,
    protected miembrosService: MiembrosService,
    protected tipoRegistroCompraService: TipoRegistroCompraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ registroCompra }) => {
      this.updateForm(registroCompra);
    });
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoRegistroCompraService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoRegistroCompra[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoRegistroCompra[]>) => response.body)
      )
      .subscribe((res: ITipoRegistroCompra[]) => (this.tiporegistrocompras = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(registroCompra: IRegistroCompra) {
    this.editForm.patchValue({
      id: registroCompra.id,
      valor: registroCompra.valor,
      fechaRegistro: registroCompra.fechaRegistro != null ? registroCompra.fechaRegistro.format(DATE_TIME_FORMAT) : null,
      idTransaccion: registroCompra.idTransaccion,
      miembro: registroCompra.miembro,
      tipoRegistro: registroCompra.tipoRegistro
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const registroCompra = this.createFromForm();
    if (registroCompra.id !== undefined) {
      this.subscribeToSaveResponse(this.registroCompraService.update(registroCompra));
    } else {
      this.subscribeToSaveResponse(this.registroCompraService.create(registroCompra));
    }
  }

  private createFromForm(): IRegistroCompra {
    return {
      ...new RegistroCompra(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      fechaRegistro:
        this.editForm.get(['fechaRegistro']).value != null
          ? moment(this.editForm.get(['fechaRegistro']).value, DATE_TIME_FORMAT)
          : undefined,
      idTransaccion: this.editForm.get(['idTransaccion']).value,
      miembro: this.editForm.get(['miembro']).value,
      tipoRegistro: this.editForm.get(['tipoRegistro']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegistroCompra>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }

  trackTipoRegistroCompraById(index: number, item: ITipoRegistroCompra) {
    return item.id;
  }
}
