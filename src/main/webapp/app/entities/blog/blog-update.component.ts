import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBlog, Blog } from 'app/shared/model/blog.model';
import { BlogService } from './blog.service';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';
import { ICategoriaContenidos } from 'app/shared/model/categoria-contenidos.model';
import { CategoriaContenidosService } from 'app/entities/categoria-contenidos';
import { IGrupos } from 'app/shared/model/grupos.model';
import { GruposService } from 'app/entities/grupos';

@Component({
  selector: 'jhi-blog-update',
  templateUrl: './blog-update.component.html'
})
export class BlogUpdateComponent implements OnInit {
  isSaving: boolean;

  miembros: IMiembros[];

  categoriacontenidos: ICategoriaContenidos[];

  grupos: IGrupos[];

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    tipoContenido: [],
    contenido: [null, [Validators.required]],
    fecha: [],
    audio: [],
    audioContentType: [],
    video: [],
    imagen1: [null, [Validators.required]],
    imagen1ContentType: [],
    imagen2: [],
    imagen2ContentType: [],
    banner: [],
    bannerContentType: [],
    estadoPublicacion: [],
    tipoConsumo: [],
    valor: [],
    impuesto: [],
    miembro: [],
    categoriaBlog: [],
    grupos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected blogService: BlogService,
    protected miembrosService: MiembrosService,
    protected categoriaContenidosService: CategoriaContenidosService,
    protected gruposService: GruposService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ blog }) => {
      this.updateForm(blog);
    });
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
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
  }

  updateForm(blog: IBlog) {
    this.editForm.patchValue({
      id: blog.id,
      titulo: blog.titulo,
      descripcion: blog.descripcion,
      tipoContenido: blog.tipoContenido,
      contenido: blog.contenido,
      fecha: blog.fecha != null ? blog.fecha.format(DATE_TIME_FORMAT) : null,
      audio: blog.audio,
      audioContentType: blog.audioContentType,
      video: blog.video,
      imagen1: blog.imagen1,
      imagen1ContentType: blog.imagen1ContentType,
      imagen2: blog.imagen2,
      imagen2ContentType: blog.imagen2ContentType,
      banner: blog.banner,
      bannerContentType: blog.bannerContentType,
      estadoPublicacion: blog.estadoPublicacion,
      tipoConsumo: blog.tipoConsumo,
      valor: blog.valor,
      impuesto: blog.impuesto,
      miembro: blog.miembro,
      categoriaBlog: blog.categoriaBlog,
      grupos: blog.grupos
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
    const blog = this.createFromForm();
    if (blog.id !== undefined) {
      this.subscribeToSaveResponse(this.blogService.update(blog));
    } else {
      this.subscribeToSaveResponse(this.blogService.create(blog));
    }
  }

  private createFromForm(): IBlog {
    return {
      ...new Blog(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      tipoContenido: this.editForm.get(['tipoContenido']).value,
      contenido: this.editForm.get(['contenido']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined,
      audioContentType: this.editForm.get(['audioContentType']).value,
      audio: this.editForm.get(['audio']).value,
      video: this.editForm.get(['video']).value,
      imagen1ContentType: this.editForm.get(['imagen1ContentType']).value,
      imagen1: this.editForm.get(['imagen1']).value,
      imagen2ContentType: this.editForm.get(['imagen2ContentType']).value,
      imagen2: this.editForm.get(['imagen2']).value,
      bannerContentType: this.editForm.get(['bannerContentType']).value,
      banner: this.editForm.get(['banner']).value,
      estadoPublicacion: this.editForm.get(['estadoPublicacion']).value,
      tipoConsumo: this.editForm.get(['tipoConsumo']).value,
      valor: this.editForm.get(['valor']).value,
      impuesto: this.editForm.get(['impuesto']).value,
      miembro: this.editForm.get(['miembro']).value,
      categoriaBlog: this.editForm.get(['categoriaBlog']).value,
      grupos: this.editForm.get(['grupos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlog>>) {
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

  trackCategoriaContenidosById(index: number, item: ICategoriaContenidos) {
    return item.id;
  }

  trackGruposById(index: number, item: IGrupos) {
    return item.id;
  }
}
