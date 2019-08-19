import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRegistroCompra, RegistroCompra } from 'app/shared/model/registro-compra.model';
import { RegistroCompraService } from './registro-compra.service';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-registro-compra-update',
  templateUrl: './registro-compra-update.component.html'
})
export class RegistroCompraUpdateComponent implements OnInit {
  isSaving: boolean;

  miembros: IMiembros[];

  editForm = this.fb.group({
    id: [],
    consumoMarket: [],
    valor: [],
    miembro: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected registroCompraService: RegistroCompraService,
    protected miembrosService: MiembrosService,
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
  }

  updateForm(registroCompra: IRegistroCompra) {
    this.editForm.patchValue({
      id: registroCompra.id,
      consumoMarket: registroCompra.consumoMarket,
      valor: registroCompra.valor,
      miembro: registroCompra.miembro
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
      consumoMarket: this.editForm.get(['consumoMarket']).value,
      valor: this.editForm.get(['valor']).value,
      miembro: this.editForm.get(['miembro']).value
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
}
