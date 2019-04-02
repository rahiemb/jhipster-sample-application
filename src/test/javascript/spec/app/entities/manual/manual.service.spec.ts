/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ManualService } from 'app/entities/manual/manual.service';
import { IManual, Manual } from 'app/shared/model/manual.model';

describe('Service Tests', () => {
    describe('Manual Service', () => {
        let injector: TestBed;
        let service: ManualService;
        let httpMock: HttpTestingController;
        let elemDefault: IManual;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ManualService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Manual(0, 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', 'AAAAAAA', false, false, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        retiredDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Manual', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        retiredDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        retiredDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Manual(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Manual', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        code: 'BBBBBB',
                        enabled: true,
                        link: 'BBBBBB',
                        description: 'BBBBBB',
                        trackChangesEnabled: true,
                        retired: true,
                        retiredDate: currentDate.format(DATE_FORMAT),
                        retiredNote: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        retiredDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Manual', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        code: 'BBBBBB',
                        enabled: true,
                        link: 'BBBBBB',
                        description: 'BBBBBB',
                        trackChangesEnabled: true,
                        retired: true,
                        retiredDate: currentDate.format(DATE_FORMAT),
                        retiredNote: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        retiredDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Manual', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
