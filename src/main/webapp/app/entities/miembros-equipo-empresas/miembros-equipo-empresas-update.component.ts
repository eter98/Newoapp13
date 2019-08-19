import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMiembrosEquipoEmpresas, MiembrosEquipoEmpresas } from 'app/shared/model/miembros-equipo-empresas.model';
import { MiembrosEquipoEmpresasService } from './miembros-equipo-empresas.service';
import { IEquipoEmpresas } from 'app/shared/model/equipo-empresas.model';
import { EquipoEmpresasService } from 'app/entities/equipo-empresas';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-miembros-equipo-empresas-update',
  templateUrl: './miembros-equipo-empresas-update.component.html'
})
export class MiembrosEquipoEmpresasUpdateComponent implements OnInit {
  isSaving: boolean;

  equipoempresas: IEquipoEmpresas[];

  miembros: IMiembros[];

  editForm = this.fb.group({
    id: [],
    equipo: [],
    miembro: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected miembrosEquipoEmpresasService: MiembrosEquipoEmpresasService,
    protected equipoEmpresasService: EquipoEmpresasService,
    protected miembrosService: MiembrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ miembrosEquipoEmpresas }) => {
      this.updateForm(miembrosEquipoEmpresas);
    });
    this.equipoEmpresasService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEquipoEmpresas[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEquipoEmpresas[]>) => response.body)
      )
      .subscribe((res: IEquipoEmpresas[]) => (this.equipoempresas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(miembrosEquipoEmpresas: IMiembrosEquipoEmpresas) {
    this.editForm.patchValue({
      id: miembrosEquipoEmpresas.id,
      equipo: miembrosEquipoEmpresas.equipo,
      miembro: miembrosEquipoEmpresas.miembro
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const miembrosEquipoEmpresas = this.createFromForm();
    if (miembrosEquipoEmpresas.id !== undefined) {
      this.subscribeToSaveResponse(this.miembrosEquipoEmpresasService.update(miembrosEquipoEmpresas));
    } else {
      this.subscribeToSaveResponse(this.miembrosEquipoEmpresasService.create(miembrosEquipoEmpresas));
    }
  }

  private createFromForm(): IMiembrosEquipoEmpresas {
    return {
      ...new MiembrosEquipoEmpresas(),
      id: this.editForm.get(['id']).value,
      equipo: this.editForm.get(['equipo']).value,
      miembro: this.editForm.get(['miembro']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMiembrosEquipoEmpresas>>) {
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

  trackEquipoEmpresasById(index: number, item: IEquipoEmpresas) {
    return item.id;
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }
}
