import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMiembrosGrupo, MiembrosGrupo } from 'app/shared/model/miembros-grupo.model';
import { MiembrosGrupoService } from './miembros-grupo.service';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';
import { IGrupos } from 'app/shared/model/grupos.model';
import { GruposService } from 'app/entities/grupos';

@Component({
  selector: 'jhi-miembros-grupo-update',
  templateUrl: './miembros-grupo-update.component.html'
})
export class MiembrosGrupoUpdateComponent implements OnInit {
  isSaving: boolean;

  miembros: IMiembros[];

  grupos: IGrupos[];

  editForm = this.fb.group({
    id: [],
    miembro: [],
    grupo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected miembrosGrupoService: MiembrosGrupoService,
    protected miembrosService: MiembrosService,
    protected gruposService: GruposService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ miembrosGrupo }) => {
      this.updateForm(miembrosGrupo);
    });
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.gruposService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGrupos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGrupos[]>) => response.body)
      )
      .subscribe((res: IGrupos[]) => (this.grupos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(miembrosGrupo: IMiembrosGrupo) {
    this.editForm.patchValue({
      id: miembrosGrupo.id,
      miembro: miembrosGrupo.miembro,
      grupo: miembrosGrupo.grupo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const miembrosGrupo = this.createFromForm();
    if (miembrosGrupo.id !== undefined) {
      this.subscribeToSaveResponse(this.miembrosGrupoService.update(miembrosGrupo));
    } else {
      this.subscribeToSaveResponse(this.miembrosGrupoService.create(miembrosGrupo));
    }
  }

  private createFromForm(): IMiembrosGrupo {
    return {
      ...new MiembrosGrupo(),
      id: this.editForm.get(['id']).value,
      miembro: this.editForm.get(['miembro']).value,
      grupo: this.editForm.get(['grupo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMiembrosGrupo>>) {
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

  trackGruposById(index: number, item: IGrupos) {
    return item.id;
  }
}
