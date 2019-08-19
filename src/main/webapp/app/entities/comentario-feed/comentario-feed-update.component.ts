import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IComentarioFeed, ComentarioFeed } from 'app/shared/model/comentario-feed.model';
import { ComentarioFeedService } from './comentario-feed.service';
import { IFeed } from 'app/shared/model/feed.model';
import { FeedService } from 'app/entities/feed';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-comentario-feed-update',
  templateUrl: './comentario-feed-update.component.html'
})
export class ComentarioFeedUpdateComponent implements OnInit {
  isSaving: boolean;

  feeds: IFeed[];

  miembros: IMiembros[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    comentario: [null, [Validators.required]],
    fecha: [],
    meGusta: [],
    seguir: [],
    feed: [],
    miembroComenta: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected comentarioFeedService: ComentarioFeedService,
    protected feedService: FeedService,
    protected miembrosService: MiembrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comentarioFeed }) => {
      this.updateForm(comentarioFeed);
    });
    this.feedService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFeed[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFeed[]>) => response.body)
      )
      .subscribe((res: IFeed[]) => (this.feeds = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(comentarioFeed: IComentarioFeed) {
    this.editForm.patchValue({
      id: comentarioFeed.id,
      comentario: comentarioFeed.comentario,
      fecha: comentarioFeed.fecha,
      meGusta: comentarioFeed.meGusta,
      seguir: comentarioFeed.seguir,
      feed: comentarioFeed.feed,
      miembroComenta: comentarioFeed.miembroComenta
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
    const comentarioFeed = this.createFromForm();
    if (comentarioFeed.id !== undefined) {
      this.subscribeToSaveResponse(this.comentarioFeedService.update(comentarioFeed));
    } else {
      this.subscribeToSaveResponse(this.comentarioFeedService.create(comentarioFeed));
    }
  }

  private createFromForm(): IComentarioFeed {
    return {
      ...new ComentarioFeed(),
      id: this.editForm.get(['id']).value,
      comentario: this.editForm.get(['comentario']).value,
      fecha: this.editForm.get(['fecha']).value,
      meGusta: this.editForm.get(['meGusta']).value,
      seguir: this.editForm.get(['seguir']).value,
      feed: this.editForm.get(['feed']).value,
      miembroComenta: this.editForm.get(['miembroComenta']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentarioFeed>>) {
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

  trackFeedById(index: number, item: IFeed) {
    return item.id;
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }
}
