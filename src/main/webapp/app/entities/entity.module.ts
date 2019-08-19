import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'miembros',
        loadChildren: () => import('./miembros/miembros.module').then(m => m.NewoApp13MiembrosModule)
      },
      {
        path: 'entrada-miembros',
        loadChildren: () => import('./entrada-miembros/entrada-miembros.module').then(m => m.NewoApp13EntradaMiembrosModule)
      },
      {
        path: 'invitados',
        loadChildren: () => import('./invitados/invitados.module').then(m => m.NewoApp13InvitadosModule)
      },
      {
        path: 'entrada-invitados',
        loadChildren: () => import('./entrada-invitados/entrada-invitados.module').then(m => m.NewoApp13EntradaInvitadosModule)
      },
      {
        path: 'sedes',
        loadChildren: () => import('./sedes/sedes.module').then(m => m.NewoApp13SedesModule)
      },
      {
        path: 'espacio-libre',
        loadChildren: () => import('./espacio-libre/espacio-libre.module').then(m => m.NewoApp13EspacioLibreModule)
      },
      {
        path: 'tipo-espacio',
        loadChildren: () => import('./tipo-espacio/tipo-espacio.module').then(m => m.NewoApp13TipoEspacioModule)
      },
      {
        path: 'host-sede',
        loadChildren: () => import('./host-sede/host-sede.module').then(m => m.NewoApp13HostSedeModule)
      },
      {
        path: 'reservas',
        loadChildren: () => import('./reservas/reservas.module').then(m => m.NewoApp13ReservasModule)
      },
      {
        path: 'espacios-reserva',
        loadChildren: () => import('./espacios-reserva/espacios-reserva.module').then(m => m.NewoApp13EspaciosReservaModule)
      },
      {
        path: 'registro-compra',
        loadChildren: () => import('./registro-compra/registro-compra.module').then(m => m.NewoApp13RegistroCompraModule)
      },
      {
        path: 'tipo-registro-compra',
        loadChildren: () => import('./tipo-registro-compra/tipo-registro-compra.module').then(m => m.NewoApp13TipoRegistroCompraModule)
      },
      {
        path: 'facturacion',
        loadChildren: () => import('./facturacion/facturacion.module').then(m => m.NewoApp13FacturacionModule)
      },
      {
        path: 'equipo-empresas',
        loadChildren: () => import('./equipo-empresas/equipo-empresas.module').then(m => m.NewoApp13EquipoEmpresasModule)
      },
      {
        path: 'miembros-equipo-empresas',
        loadChildren: () =>
          import('./miembros-equipo-empresas/miembros-equipo-empresas.module').then(m => m.NewoApp13MiembrosEquipoEmpresasModule)
      },
      {
        path: 'cuenta-asociada',
        loadChildren: () => import('./cuenta-asociada/cuenta-asociada.module').then(m => m.NewoApp13CuentaAsociadaModule)
      },
      {
        path: 'beneficio',
        loadChildren: () => import('./beneficio/beneficio.module').then(m => m.NewoApp13BeneficioModule)
      },
      {
        path: 'perfil-equipo-empresa',
        loadChildren: () => import('./perfil-equipo-empresa/perfil-equipo-empresa.module').then(m => m.NewoApp13PerfilEquipoEmpresaModule)
      },
      {
        path: 'empresa',
        loadChildren: () => import('./empresa/empresa.module').then(m => m.NewoApp13EmpresaModule)
      },
      {
        path: 'landing',
        loadChildren: () => import('./landing/landing.module').then(m => m.NewoApp13LandingModule)
      },
      {
        path: 'productos-servicios',
        loadChildren: () => import('./productos-servicios/productos-servicios.module').then(m => m.NewoApp13ProductosServiciosModule)
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.NewoApp13PaisModule)
      },
      {
        path: 'ciudad',
        loadChildren: () => import('./ciudad/ciudad.module').then(m => m.NewoApp13CiudadModule)
      },
      {
        path: 'blog',
        loadChildren: () => import('./blog/blog.module').then(m => m.NewoApp13BlogModule)
      },
      {
        path: 'comentario-blog',
        loadChildren: () => import('./comentario-blog/comentario-blog.module').then(m => m.NewoApp13ComentarioBlogModule)
      },
      {
        path: 'feed',
        loadChildren: () => import('./feed/feed.module').then(m => m.NewoApp13FeedModule)
      },
      {
        path: 'comentario-feed',
        loadChildren: () => import('./comentario-feed/comentario-feed.module').then(m => m.NewoApp13ComentarioFeedModule)
      },
      {
        path: 'chat',
        loadChildren: () => import('./chat/chat.module').then(m => m.NewoApp13ChatModule)
      },
      {
        path: 'chats-listado',
        loadChildren: () => import('./chats-listado/chats-listado.module').then(m => m.NewoApp13ChatsListadoModule)
      },
      {
        path: 'evento',
        loadChildren: () => import('./evento/evento.module').then(m => m.NewoApp13EventoModule)
      },
      {
        path: 'categoria-contenidos',
        loadChildren: () => import('./categoria-contenidos/categoria-contenidos.module').then(m => m.NewoApp13CategoriaContenidosModule)
      },
      {
        path: 'grupos',
        loadChildren: () => import('./grupos/grupos.module').then(m => m.NewoApp13GruposModule)
      },
      {
        path: 'miembros-grupo',
        loadChildren: () => import('./miembros-grupo/miembros-grupo.module').then(m => m.NewoApp13MiembrosGrupoModule)
      },
      {
        path: 'recursos-fisicos',
        loadChildren: () => import('./recursos-fisicos/recursos-fisicos.module').then(m => m.NewoApp13RecursosFisicosModule)
      },
      {
        path: 'uso-recurso-fisico',
        loadChildren: () => import('./uso-recurso-fisico/uso-recurso-fisico.module').then(m => m.NewoApp13UsoRecursoFisicoModule)
      },
      {
        path: 'tipo-recurso',
        loadChildren: () => import('./tipo-recurso/tipo-recurso.module').then(m => m.NewoApp13TipoRecursoModule)
      },
      {
        path: 'consumo-market',
        loadChildren: () => import('./consumo-market/consumo-market.module').then(m => m.NewoApp13ConsumoMarketModule)
      },
      {
        path: 'prepago-consumo',
        loadChildren: () => import('./prepago-consumo/prepago-consumo.module').then(m => m.NewoApp13PrepagoConsumoModule)
      },
      {
        path: 'margen-newo-eventos',
        loadChildren: () => import('./margen-newo-eventos/margen-newo-eventos.module').then(m => m.NewoApp13MargenNewoEventosModule)
      },
      {
        path: 'margen-newo-grupos',
        loadChildren: () => import('./margen-newo-grupos/margen-newo-grupos.module').then(m => m.NewoApp13MargenNewoGruposModule)
      },
      {
        path: 'margen-newo-blog',
        loadChildren: () => import('./margen-newo-blog/margen-newo-blog.module').then(m => m.NewoApp13MargenNewoBlogModule)
      },
      {
        path: 'margen-newo-productos',
        loadChildren: () => import('./margen-newo-productos/margen-newo-productos.module').then(m => m.NewoApp13MargenNewoProductosModule)
      },
      {
        path: 'tipo-prepago-consumo',
        loadChildren: () => import('./tipo-prepago-consumo/tipo-prepago-consumo.module').then(m => m.NewoApp13TipoPrepagoConsumoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13EntityModule {}
