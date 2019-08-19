import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPais, Pais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';

@Component({
  selector: 'jhi-pais-update',
  templateUrl: './pais-update.component.html'
})
export class PaisUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombrePais: [null, [Validators.required]]
  });

  constructor(protected paisService: PaisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pais }) => {
      this.updateForm(pais);
    });
  }

  updateForm(pais: IPais) {
    this.editForm.patchValue({
      id: pais.id,
      nombrePais: pais.nombrePais
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pais = this.createFromForm();
    if (pais.id !== undefined) {
      this.subscribeToSaveResponse(this.paisService.update(pais));
    } else {
      this.subscribeToSaveResponse(this.paisService.create(pais));
    }
  }

  private createFromForm(): IPais {
    return {
      ...new Pais(),
      id: this.editForm.get(['id']).value,
      nombrePais: this.editForm.get(['nombrePais']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPais>>) {
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
