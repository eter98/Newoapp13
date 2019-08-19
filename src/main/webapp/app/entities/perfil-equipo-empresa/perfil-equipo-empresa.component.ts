import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPerfilEquipoEmpresa } from 'app/shared/model/perfil-equipo-empresa.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PerfilEquipoEmpresaService } from './perfil-equipo-empresa.service';

@Component({
  selector: 'jhi-perfil-equipo-empresa',
  templateUrl: './perfil-equipo-empresa.component.html'
})
export class PerfilEquipoEmpresaComponent implements OnInit, OnDestroy {
  perfilEquipoEmpresas: IPerfilEquipoEmpresa[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  constructor(
    protected perfilEquipoEmpresaService: PerfilEquipoEmpresaService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.perfilEquipoEmpresas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.perfilEquipoEmpresaService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IPerfilEquipoEmpresa[]>) => this.paginatePerfilEquipoEmpresas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.perfilEquipoEmpresaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPerfilEquipoEmpresa[]>) => this.paginatePerfilEquipoEmpresas(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  reset() {
    this.page = 0;
    this.perfilEquipoEmpresas = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.perfilEquipoEmpresas = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch = '';
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.perfilEquipoEmpresas = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPerfilEquipoEmpresas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPerfilEquipoEmpresa) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPerfilEquipoEmpresas() {
    this.eventSubscriber = this.eventManager.subscribe('perfilEquipoEmpresaListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePerfilEquipoEmpresas(data: IPerfilEquipoEmpresa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.perfilEquipoEmpresas.push(data[i]);
    }
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
