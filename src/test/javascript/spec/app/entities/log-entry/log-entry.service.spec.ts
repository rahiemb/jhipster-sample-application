/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LogEntryService } from 'app/entities/log-entry/log-entry.service';
import { ILogEntry, LogEntry, ObjectType, LogLevel } from 'app/shared/model/log-entry.model';

describe('Service Tests', () => {
    describe('LogEntry Service', () => {
        let injector: TestBed;
        let service: LogEntryService;
        let httpMock: HttpTestingController;
        let elemDefault: ILogEntry;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LogEntryService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new LogEntry(0, ObjectType.CATEGORY, LogLevel.INFO, 0, 'AAAAAAA', 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        timestamp: currentDate.format(DATE_FORMAT)
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

            it('should create a LogEntry', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        timestamp: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        timestamp: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new LogEntry(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a LogEntry', async () => {
                const returnedFromService = Object.assign(
                    {
                        type: 'BBBBBB',
                        level: 'BBBBBB',
                        objectId: 1,
                        details: 'BBBBBB',
                        user: 'BBBBBB',
                        timestamp: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        timestamp: currentDate
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

            it('should return a list of LogEntry', async () => {
                const returnedFromService = Object.assign(
                    {
                        type: 'BBBBBB',
                        level: 'BBBBBB',
                        objectId: 1,
                        details: 'BBBBBB',
                        user: 'BBBBBB',
                        timestamp: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        timestamp: currentDate
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

            it('should delete a LogEntry', async () => {
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
