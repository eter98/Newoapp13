import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';

@Component({
  selector: 'jhi-chat-delete-dialog',
  templateUrl: './chat-delete-dialog.component.html'
})
export class ChatDeleteDialogComponent {
  chat: IChat;

  constructor(protected chatService: ChatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.chatService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'chatListModification',
        content: 'Deleted an chat'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-chat-delete-popup',
  template: ''
})
export class ChatDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chat }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ChatDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.chat = chat;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/chat', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/chat', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
