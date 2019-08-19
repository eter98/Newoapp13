import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEquipoEmpresas, EquipoEmpresas } from 'app/shared/model/equipo-empresas.model';
import { EquipoEmpresasService } from './equipo-empresas.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
  selector: 'jhi-equipo-empresas-update',
  templateUrl: './equipo-empresas-update.component.html'
})
export class EquipoEmpresasUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    telefono: [],
    correo: [],
    direccion: [],
    descripcion: [],
    logo: [],
    logoContentType: [],
    paginaWeb: [],
    conocimientosQueDomina: [null, [Validators.required]],
    temasDeInteres: [null, [Validators.required]],
    empresa: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected equipoEmpresasService: EquipoEmpresasService,
    protected empresaService: EmpresaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipoEmpresas }) => {
      this.updateForm(equipoEmpresas);
    });
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(equipoEmpresas: IEquipoEmpresas) {
    this.editForm.patchValue({
      id: equipoEmpresas.id,
      nombre: equipoEmpresas.nombre,
      telefono: equipoEmpresas.telefono,
      correo: equipoEmpresas.correo,
      direccion: equipoEmpresas.direccion,
      descripcion: equipoEmpresas.descripcion,
      logo: equipoEmpresas.logo,
      logoContentType: equipoEmpresas.logoContentType,
      paginaWeb: equipoEmpresas.paginaWeb,
      conocimientosQueDomina: equipoEmpresas.conocimientosQueDomina,
      temasDeInteres: equipoEmpresas.temasDeInteres,
      empresa: equipoEmpresas.empresa
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
    const equipoEmpresas = this.createFromForm();
    if (equipoEmpresas.id !== undefined) {
      this.subscribeToSaveResponse(this.equipoEmpresasService.update(equipoEmpresas));
    } else {
      this.subscribeToSaveResponse(this.equipoEmpresasService.create(equipoEmpresas));
    }
  }

  private createFromForm(): IEquipoEmpresas {
    return {
      ...new EquipoEmpresas(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      telefono: this.editForm.get(['telefono']).value,
      correo: this.editForm.get(['correo']).value,
      direccion: this.editForm.get(['direccion']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      logoContentType: this.editForm.get(['logoContentType']).value,
      logo: this.editForm.get(['logo']).value,
      paginaWeb: this.editForm.get(['paginaWeb']).value,
      conocimientosQueDomina: this.editForm.get(['conocimientosQueDomina']).value,
      temasDeInteres: this.editForm.get(['temasDeInteres']).value,
      empresa: this.editForm.get(['empresa']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipoEmpresas>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
