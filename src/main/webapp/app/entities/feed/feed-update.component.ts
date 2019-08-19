import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFeed, Feed } from 'app/shared/model/feed.model';
import { FeedService } from './feed.service';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';
import { ICategoriaContenidos } from 'app/shared/model/categoria-contenidos.model';
import { CategoriaContenidosService } from 'app/entities/categoria-contenidos';

@Component({
  selector: 'jhi-feed-update',
  templateUrl: './feed-update.component.html'
})
export class FeedUpdateComponent implements OnInit {
  isSaving: boolean;

  miembros: IMiembros[];

  categoriacontenidos: ICategoriaContenidos[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    imagen1: [null, [Validators.required]],
    imagen1ContentType: [],
    imagen2: [],
    imagen2ContentType: [],
    tipoContenido: [],
    contenido: [null, [Validators.required]],
    fecha: [],
    miembro: [],
    categoriaFeed: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected feedService: FeedService,
    protected miembrosService: MiembrosService,
    protected categoriaContenidosService: CategoriaContenidosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ feed }) => {
      this.updateForm(feed);
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
  }

  updateForm(feed: IFeed) {
    this.editForm.patchValue({
      id: feed.id,
      titulo: feed.titulo,
      descripcion: feed.descripcion,
      imagen1: feed.imagen1,
      imagen1ContentType: feed.imagen1ContentType,
      imagen2: feed.imagen2,
      imagen2ContentType: feed.imagen2ContentType,
      tipoContenido: feed.tipoContenido,
      contenido: feed.contenido,
      fecha: feed.fecha,
      miembro: feed.miembro,
      categoriaFeed: feed.categoriaFeed
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
    const feed = this.createFromForm();
    if (feed.id !== undefined) {
      this.subscribeToSaveResponse(this.feedService.update(feed));
    } else {
      this.subscribeToSaveResponse(this.feedService.create(feed));
    }
  }

  private createFromForm(): IFeed {
    return {
      ...new Feed(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      imagen1ContentType: this.editForm.get(['imagen1ContentType']).value,
      imagen1: this.editForm.get(['imagen1']).value,
      imagen2ContentType: this.editForm.get(['imagen2ContentType']).value,
      imagen2: this.editForm.get(['imagen2']).value,
      tipoContenido: this.editForm.get(['tipoContenido']).value,
      contenido: this.editForm.get(['contenido']).value,
      fecha: this.editForm.get(['fecha']).value,
      miembro: this.editForm.get(['miembro']).value,
      categoriaFeed: this.editForm.get(['categoriaFeed']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeed>>) {
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
}
