import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChatsListado } from 'app/shared/model/chats-listado.model';

@Component({
  selector: 'jhi-chats-listado-detail',
  templateUrl: './chats-listado-detail.component.html'
})
export class ChatsListadoDetailComponent implements OnInit {
  chatsListado: IChatsListado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chatsListado }) => {
      this.chatsListado = chatsListado;
    });
  }

  previousState() {
    window.history.back();
  }
}
