/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BlogService } from 'app/entities/blog/blog.service';
import { IBlog, Blog, Categoriad, EstadoPublicaciond, TipoConsumod, Impuestod } from 'app/shared/model/blog.model';

describe('Service Tests', () => {
  describe('Blog Service', () => {
    let injector: TestBed;
    let service: BlogService;
    let httpMock: HttpTestingController;
    let elemDefault: IBlog;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BlogService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Blog(
        0,
        'AAAAAAA',
        'AAAAAAA',
        Categoriad.GENERAL,
        'AAAAAAA',
        currentDate,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        EstadoPublicaciond.BORRADOR,
        TipoConsumod.GRATIS,
        0,
        Impuestod.IVA19
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT)
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

      it('should create a Blog', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );
        service
          .create(new Blog(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Blog', async () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            tipoContenido: 'BBBBBB',
            contenido: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            audio: 'BBBBBB',
            video: 'BBBBBB',
            imagen1: 'BBBBBB',
            imagen2: 'BBBBBB',
            banner: 'BBBBBB',
            estadoPublicacion: 'BBBBBB',
            tipoConsumo: 'BBBBBB',
            valor: 1,
            impuesto: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
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

      it('should return a list of Blog', async () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            tipoContenido: 'BBBBBB',
            contenido: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            audio: 'BBBBBB',
            video: 'BBBBBB',
            imagen1: 'BBBBBB',
            imagen2: 'BBBBBB',
            banner: 'BBBBBB',
            estadoPublicacion: 'BBBBBB',
            tipoConsumo: 'BBBBBB',
            valor: 1,
            impuesto: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate
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

      it('should delete a Blog', async () => {
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
