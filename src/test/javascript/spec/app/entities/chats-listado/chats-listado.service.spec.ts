/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ChatsListadoService } from 'app/entities/chats-listado/chats-listado.service';
import { IChatsListado, ChatsListado, Estatusd } from 'app/shared/model/chats-listado.model';

describe('Service Tests', () => {
  describe('ChatsListado Service', () => {
    let injector: TestBed;
    let service: ChatsListadoService;
    let httpMock: HttpTestingController;
    let elemDefault: IChatsListado;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ChatsListadoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ChatsListado(0, 'AAAAAAA', Estatusd.EnLinea, 0, 0, 'AAAAAAA', currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            sendTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ChatsListado', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            sendTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            sendTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new ChatsListado(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ChatsListado', async () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            estatus: 'BBBBBB',
            count: 1,
            badge: 1,
            time: 'BBBBBB',
            sendTime: currentDate.format(DATE_TIME_FORMAT),
            grupo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sendTime: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ChatsListado', async () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            estatus: 'BBBBBB',
            count: 1,
            badge: 1,
            time: 'BBBBBB',
            sendTime: currentDate.format(DATE_TIME_FORMAT),
            grupo: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            sendTime: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ChatsListado', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
