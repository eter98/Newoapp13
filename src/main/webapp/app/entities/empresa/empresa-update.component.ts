import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving: boolean;

  miembros: IMiembros[];

  editForm = this.fb.group({
    id: [],
    razonSocial: [null, [Validators.required]],
    nit: [null, [Validators.required, Validators.minLength(9), Validators.maxLength(13)]],
    direccion: [],
    telefono: [],
    correo: [],
    web: [],
    celular: [],
    biografia: [null, [Validators.required]],
    imagen1: [],
    imagen1ContentType: [],
    imagen2: [],
    imagen2ContentType: [],
    imagen3: [],
    imagen3ContentType: [],
    facebook: [],
    instagram: [],
    idGoogle: [],
    twiter: [],
    conocimientosQueDomina: [null, [Validators.required]],
    temasDeInteres: [null, [Validators.required]],
    miembro: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected empresaService: EmpresaService,
    protected miembrosService: MiembrosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.updateForm(empresa);
    });
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(empresa: IEmpresa) {
    this.editForm.patchValue({
      id: empresa.id,
      razonSocial: empresa.razonSocial,
      nit: empresa.nit,
      direccion: empresa.direccion,
      telefono: empresa.telefono,
      correo: empresa.correo,
      web: empresa.web,
      celular: empresa.celular,
      biografia: empresa.biografia,
      imagen1: empresa.imagen1,
      imagen1ContentType: empresa.imagen1ContentType,
      imagen2: empresa.imagen2,
      imagen2ContentType: empresa.imagen2ContentType,
      imagen3: empresa.imagen3,
      imagen3ContentType: empresa.imagen3ContentType,
      facebook: empresa.facebook,
      instagram: empresa.instagram,
      idGoogle: empresa.idGoogle,
      twiter: empresa.twiter,
      conocimientosQueDomina: empresa.conocimientosQueDomina,
      temasDeInteres: empresa.temasDeInteres,
      miembro: empresa.miembro
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  private createFromForm(): IEmpresa {
    return {
      ...new Empresa(),
      id: this.editForm.get(['id']).value,
      razonSocial: this.editForm.get(['razonSocial']).value,
      nit: this.editForm.get(['nit']).value,
      direccion: this.editForm.get(['direccion']).value,
      telefono: this.editForm.get(['telefono']).value,
      correo: this.editForm.get(['correo']).value,
      web: this.editForm.get(['web']).value,
      celular: this.editForm.get(['celular']).value,
      biografia: this.editForm.get(['biografia']).value,
      imagen1ContentType: this.editForm.get(['imagen1ContentType']).value,
      imagen1: this.editForm.get(['imagen1']).value,
      imagen2ContentType: this.editForm.get(['imagen2ContentType']).value,
      imagen2: this.editForm.get(['imagen2']).value,
      imagen3ContentType: this.editForm.get(['imagen3ContentType']).value,
      imagen3: this.editForm.get(['imagen3']).value,
      facebook: this.editForm.get(['facebook']).value,
      instagram: this.editForm.get(['instagram']).value,
      idGoogle: this.editForm.get(['idGoogle']).value,
      twiter: this.editForm.get(['twiter']).value,
      conocimientosQueDomina: this.editForm.get(['conocimientosQueDomina']).value,
      temasDeInteres: this.editForm.get(['temasDeInteres']).value,
      miembro: this.editForm.get(['miembro']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
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
