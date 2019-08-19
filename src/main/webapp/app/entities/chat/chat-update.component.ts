import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IChat, Chat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';
import { IChatsListado } from 'app/shared/model/chats-listado.model';
import { ChatsListadoService } from 'app/entities/chats-listado';
import { IMiembros } from 'app/shared/model/miembros.model';
import { MiembrosService } from 'app/entities/miembros';

@Component({
  selector: 'jhi-chat-update',
  templateUrl: './chat-update.component.html'
})
export class ChatUpdateComponent implements OnInit {
  isSaving: boolean;

  chatslistados: IChatsListado[];

  miembros: IMiembros[];

  editForm = this.fb.group({
    id: [],
    mensaje: [null, [Validators.required]],
    sender: [],
    read: [],
    delivered: [],
    sent: [],
    fecha: [],
    chatsListado: [],
    de: [],
    para: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected chatService: ChatService,
    protected chatsListadoService: ChatsListadoService,
    protected miembrosService: MiembrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ chat }) => {
      this.updateForm(chat);
    });
    this.chatsListadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChatsListado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChatsListado[]>) => response.body)
      )
      .subscribe((res: IChatsListado[]) => (this.chatslistados = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.miembrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiembros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiembros[]>) => response.body)
      )
      .subscribe((res: IMiembros[]) => (this.miembros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(chat: IChat) {
    this.editForm.patchValue({
      id: chat.id,
      mensaje: chat.mensaje,
      sender: chat.sender,
      read: chat.read,
      delivered: chat.delivered,
      sent: chat.sent,
      fecha: chat.fecha != null ? chat.fecha.format(DATE_TIME_FORMAT) : null,
      chatsListado: chat.chatsListado,
      de: chat.de,
      para: chat.para
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const chat = this.createFromForm();
    if (chat.id !== undefined) {
      this.subscribeToSaveResponse(this.chatService.update(chat));
    } else {
      this.subscribeToSaveResponse(this.chatService.create(chat));
    }
  }

  private createFromForm(): IChat {
    return {
      ...new Chat(),
      id: this.editForm.get(['id']).value,
      mensaje: this.editForm.get(['mensaje']).value,
      sender: this.editForm.get(['sender']).value,
      read: this.editForm.get(['read']).value,
      delivered: this.editForm.get(['delivered']).value,
      sent: this.editForm.get(['sent']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined,
      chatsListado: this.editForm.get(['chatsListado']).value,
      de: this.editForm.get(['de']).value,
      para: this.editForm.get(['para']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChat>>) {
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

  trackChatsListadoById(index: number, item: IChatsListado) {
    return item.id;
  }

  trackMiembrosById(index: number, item: IMiembros) {
    return item.id;
  }
}
