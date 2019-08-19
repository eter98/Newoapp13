import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IComentarioBlog, ComentarioBlog } from 'app/shared/model/comentario-blog.model';
import { ComentarioBlogService } from './comentario-blog.service';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from 'app/entities/blog';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-comentario-blog-update',
  templateUrl: './comentario-blog-update.component.html'
})
export class ComentarioBlogUpdateComponent implements OnInit {
  isSaving: boolean;

  blogs: IBlog[];

  miembros: IMiembros[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    comentario: [null, [Validators.required]],
    fecha: [],
    meGusta: [],
    seguir: [],
    blog: [],
    miembroComenta: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected comentarioBlogService: ComentarioBlogService,
    protected blogService: BlogService,
    protected miembrosService: MiembrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comentarioBlog }) => {
      this.updateForm(comentarioBlog);
    });
    this.blogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBlog[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBlog[]>) => response.body)
      )
      .subscribe((res: IBlog[]) => (this.blogs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(comentarioBlog: IComentarioBlog) {
    this.editForm.patchValue({
      id: comentarioBlog.id,
      comentario: comentarioBlog.comentario,
      fecha: comentarioBlog.fecha,
      meGusta: comentarioBlog.meGusta,
      seguir: comentarioBlog.seguir,
      blog: comentarioBlog.blog,
      miembroComenta: comentarioBlog.miembroComenta
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const comentarioBlog = this.createFromForm();
    if (comentarioBlog.id !== undefined) {
      this.subscribeToSaveResponse(this.comentarioBlogService.update(comentarioBlog));
    } else {
      this.subscribeToSaveResponse(this.comentarioBlogService.create(comentarioBlog));
    }
  }

  private createFromForm(): IComentarioBlog {
    return {
      ...new ComentarioBlog(),
      id: this.editForm.get(['id']).value,
      comentario: this.editForm.get(['comentario']).value,
      fecha: this.editForm.get(['fecha']).value,
      meGusta: this.editForm.get(['meGusta']).value,
      seguir: this.editForm.get(['seguir']).value,
      blog: this.editForm.get(['blog']).value,
      miembroComenta: this.editForm.get(['miembroComenta']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentarioBlog>>) {
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

  trackBlogById(index: number, item: IBlog) {
    return item.id;
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }
}
