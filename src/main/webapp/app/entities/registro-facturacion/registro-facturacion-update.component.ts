import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRegistroFacturacion, RegistroFacturacion } from 'app/shared/model/registro-facturacion.model';
import { RegistroFacturacionService } from './registro-facturacion.service';
import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';
import { TipoRegistroCompraService } from 'app/entities/tipo-registro-compra';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-registro-facturacion-update',
  templateUrl: './registro-facturacion-update.component.html'
})
export class RegistroFacturacionUpdateComponent implements OnInit {
  isSaving: boolean;

  tiporegistrocompras: ITipoRegistroCompra[];

  miembros: IMiembros[];
  fechaFacturacionDp: any;

  editForm = this.fb.group({
    id: [],
    valor: [],
    fechaRegistro: [],
    fechaFacturacion: [],
    tipoRegistro: [],
    miembro: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected registroFacturacionService: RegistroFacturacionService,
    protected tipoRegistroCompraService: TipoRegistroCompraService,
    protected miembrosService: MiembrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ registroFacturacion }) => {
      this.updateForm(registroFacturacion);
    });
    this.tipoRegistroCompraService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoRegistroCompra[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoRegistroCompra[]>) => response.body)
      )
      .subscribe((res: ITipoRegistroCompra[]) => (this.tiporegistrocompras = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(registroFacturacion: IRegistroFacturacion) {
    this.editForm.patchValue({
      id: registroFacturacion.id,
      valor: registroFacturacion.valor,
      fechaRegistro: registroFacturacion.fechaRegistro != null ? registroFacturacion.fechaRegistro.format(DATE_TIME_FORMAT) : null,
      fechaFacturacion: registroFacturacion.fechaFacturacion,
      tipoRegistro: registroFacturacion.tipoRegistro,
      miembro: registroFacturacion.miembro
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const registroFacturacion = this.createFromForm();
    if (registroFacturacion.id !== undefined) {
      this.subscribeToSaveResponse(this.registroFacturacionService.update(registroFacturacion));
    } else {
      this.subscribeToSaveResponse(this.registroFacturacionService.create(registroFacturacion));
    }
  }

  private createFromForm(): IRegistroFacturacion {
    return {
      ...new RegistroFacturacion(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      fechaRegistro:
        this.editForm.get(['fechaRegistro']).value != null
          ? moment(this.editForm.get(['fechaRegistro']).value, DATE_TIME_FORMAT)
          : undefined,
      fechaFacturacion: this.editForm.get(['fechaFacturacion']).value,
      tipoRegistro: this.editForm.get(['tipoRegistro']).value,
      miembro: this.editForm.get(['miembro']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegistroFacturacion>>) {
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

  trackTipoRegistroCompraById(index: number, item: ITipoRegistroCompra) {
    return item.id;
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }
}
