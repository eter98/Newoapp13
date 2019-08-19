import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEvento, Evento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { ICategoriaContenidos } from 'app/shared/model/categoria-contenidos.model';
import { CategoriaContenidosService } from 'app/entities/categoria-contenidos';
import { IGrupos } from 'app/shared/model/grupos.model';
import { GruposService } from 'app/entities/grupos';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-evento-update',
  templateUrl: './evento-update.component.html'
})
export class EventoUpdateComponent implements OnInit {
  isSaving: boolean;

  categoriacontenidos: ICategoriaContenidos[];

  grupos: IGrupos[];

  miembros: IMiembros[];

  editForm = this.fb.group({
    id: [],
    nombreEvento: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    contenido: [null, [Validators.required]],
    audio: [],
    audioContentType: [],
    video: [],
    videoContentType: [],
    imagen1: [null, [Validators.required]],
    imagen1ContentType: [],
    imagen2: [],
    imagen2ContentType: [],
    banner: [],
    bannerContentType: [],
    tipoConsumo: [],
    valor: [],
    impuesto: [],
    tipoEvento: [],
    categoriaEvento: [],
    grupos: [],
    miembro: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected eventoService: EventoService,
    protected categoriaContenidosService: CategoriaContenidosService,
    protected gruposService: GruposService,
    protected miembrosService: MiembrosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ evento }) => {
      this.updateForm(evento);
    });
    this.categoriaContenidosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoriaContenidos[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoriaContenidos[]>) => response.body)
      )
      .subscribe((res: ICategoriaContenidos[]) => (this.categoriacontenidos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.gruposService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGrupos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGrupos[]>) => response.body)
      )
      .subscribe((res: IGrupos[]) => (this.grupos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(evento: IEvento) {
    this.editForm.patchValue({
      id: evento.id,
      nombreEvento: evento.nombreEvento,
      descripcion: evento.descripcion,
      contenido: evento.contenido,
      audio: evento.audio,
      audioContentType: evento.audioContentType,
      video: evento.video,
      videoContentType: evento.videoContentType,
      imagen1: evento.imagen1,
      imagen1ContentType: evento.imagen1ContentType,
      imagen2: evento.imagen2,
      imagen2ContentType: evento.imagen2ContentType,
      banner: evento.banner,
      bannerContentType: evento.bannerContentType,
      tipoConsumo: evento.tipoConsumo,
      valor: evento.valor,
      impuesto: evento.impuesto,
      tipoEvento: evento.tipoEvento,
      categoriaEvento: evento.categoriaEvento,
      grupos: evento.grupos,
      miembro: evento.miembro
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
    const evento = this.createFromForm();
    if (evento.id !== undefined) {
      this.subscribeToSaveResponse(this.eventoService.update(evento));
    } else {
      this.subscribeToSaveResponse(this.eventoService.create(evento));
    }
  }

  private createFromForm(): IEvento {
    return {
      ...new Evento(),
      id: this.editForm.get(['id']).value,
      nombreEvento: this.editForm.get(['nombreEvento']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      contenido: this.editForm.get(['contenido']).value,
      audioContentType: this.editForm.get(['audioContentType']).value,
      audio: this.editForm.get(['audio']).value,
      videoContentType: this.editForm.get(['videoContentType']).value,
      video: this.editForm.get(['video']).value,
      imagen1ContentType: this.editForm.get(['imagen1ContentType']).value,
      imagen1: this.editForm.get(['imagen1']).value,
      imagen2ContentType: this.editForm.get(['imagen2ContentType']).value,
      imagen2: this.editForm.get(['imagen2']).value,
      bannerContentType: this.editForm.get(['bannerContentType']).value,
      banner: this.editForm.get(['banner']).value,
      tipoConsumo: this.editForm.get(['tipoConsumo']).value,
      valor: this.editForm.get(['valor']).value,
      impuesto: this.editForm.get(['impuesto']).value,
      tipoEvento: this.editForm.get(['tipoEvento']).value,
      categoriaEvento: this.editForm.get(['categoriaEvento']).value,
      grupos: this.editForm.get(['grupos']).value,
      miembro: this.editForm.get(['miembro']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvento>>) {
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

  trackCategoriaContenidosById(index: number, item: ICategoriaContenidos) {
    return item.id;
  }

  trackGruposById(index: number, item: IGrupos) {
    return item.id;
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }
}
