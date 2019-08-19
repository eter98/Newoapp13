import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IMiembros, Miembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from './miembros.service';
import { IUser, UserService } from 'app/core';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais';
import { ISedes } from 'app/shared/model/sedes.model';
import { SedesService } from 'app/entities/sedes';

@Component({
  selector: 'jhi-miembros-update',
  templateUrl: './miembros-update.component.html'
})
export class MiembrosUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  pais: IPais[];

  sedes: ISedes[];
  fechaNacimientoDp: any;
  fechaRegistroDp: any;

  editForm = this.fb.group({
    id: [],
    tipoDocumento: [null, [Validators.required]],
    identificacion: [null, [Validators.required]],
    fechaNacimiento: [null, [Validators.required]],
    fechaRegistro: [null, [Validators.required]],
    genero: [null, [Validators.required]],
    celular: [null, [Validators.required, Validators.minLength(11), Validators.maxLength(13)]],
    biografia: [],
    foto1: [null, [Validators.required]],
    foto1ContentType: [],
    foto2: [],
    foto2ContentType: [],
    fot3: [],
    fot3ContentType: [],
    conocimientosQueDomina: [null, [Validators.required]],
    temasDeInteres: [null, [Validators.required]],
    facebook: [],
    instagram: [],
    idGoogle: [],
    twiter: [],
    derechosDeCompra: [],
    accesoIlimitado: [],
    aliado: [],
    host: [],
    miembro: [],
    nacionalidad: [],
    sede: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected miembrosService: MiembrosService,
    protected userService: UserService,
    protected paisService: PaisService,
    protected sedesService: SedesService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ miembros }) => {
      this.updateForm(miembros);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.paisService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPais[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPais[]>) => response.body)
      )
      .subscribe((res: IPais[]) => (this.pais = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sedesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISedes[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISedes[]>) => response.body)
      )
      .subscribe((res: ISedes[]) => (this.sedes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(miembros: IMiembros) {
    this.editForm.patchValue({
      id: miembros.id,
      tipoDocumento: miembros.tipoDocumento,
      identificacion: miembros.identificacion,
      fechaNacimiento: miembros.fechaNacimiento,
      fechaRegistro: miembros.fechaRegistro,
      genero: miembros.genero,
      celular: miembros.celular,
      biografia: miembros.biografia,
      foto1: miembros.foto1,
      foto1ContentType: miembros.foto1ContentType,
      foto2: miembros.foto2,
      foto2ContentType: miembros.foto2ContentType,
      fot3: miembros.fot3,
      fot3ContentType: miembros.fot3ContentType,
      conocimientosQueDomina: miembros.conocimientosQueDomina,
      temasDeInteres: miembros.temasDeInteres,
      facebook: miembros.facebook,
      instagram: miembros.instagram,
      idGoogle: miembros.idGoogle,
      twiter: miembros.twiter,
      derechosDeCompra: miembros.derechosDeCompra,
      accesoIlimitado: miembros.accesoIlimitado,
      aliado: miembros.aliado,
      host: miembros.host,
      miembro: miembros.miembro,
      nacionalidad: miembros.nacionalidad,
      sede: miembros.sede
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
    const miembros = this.createFromForm();
    if (miembros.id !== undefined) {
      this.subscribeToSaveResponse(this.miembrosService.update(miembros));
    } else {
      this.subscribeToSaveResponse(this.miembrosService.create(miembros));
    }
  }

  private createFromForm(): IMiembros {
    return {
      ...new Miembros(),
      id: this.editForm.get(['id']).value,
      tipoDocumento: this.editForm.get(['tipoDocumento']).value,
      identificacion: this.editForm.get(['identificacion']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      fechaRegistro: this.editForm.get(['fechaRegistro']).value,
      genero: this.editForm.get(['genero']).value,
      celular: this.editForm.get(['celular']).value,
      biografia: this.editForm.get(['biografia']).value,
      foto1ContentType: this.editForm.get(['foto1ContentType']).value,
      foto1: this.editForm.get(['foto1']).value,
      foto2ContentType: this.editForm.get(['foto2ContentType']).value,
      foto2: this.editForm.get(['foto2']).value,
      fot3ContentType: this.editForm.get(['fot3ContentType']).value,
      fot3: this.editForm.get(['fot3']).value,
      conocimientosQueDomina: this.editForm.get(['conocimientosQueDomina']).value,
      temasDeInteres: this.editForm.get(['temasDeInteres']).value,
      facebook: this.editForm.get(['facebook']).value,
      instagram: this.editForm.get(['instagram']).value,
      idGoogle: this.editForm.get(['idGoogle']).value,
      twiter: this.editForm.get(['twiter']).value,
      derechosDeCompra: this.editForm.get(['derechosDeCompra']).value,
      accesoIlimitado: this.editForm.get(['accesoIlimitado']).value,
      aliado: this.editForm.get(['aliado']).value,
      host: this.editForm.get(['host']).value,
      miembro: this.editForm.get(['miembro']).value,
      nacionalidad: this.editForm.get(['nacionalidad']).value,
      sede: this.editForm.get(['sede']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMiembros>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackPaisById(index: number, item: IPais) {
    return item.id;
  }

  trackSedesById(index: number, item: ISedes) {
    return item.id;
  }
}
