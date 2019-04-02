/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { NotificationService } from 'app/entities/notification/notification.service';
import { INotification, Notification, TimeInterval } from 'app/shared/model/notification.model';

describe('Service Tests', () => {
    describe('Notification Service', () => {
        let injector: TestBed;
        let service: NotificationService;
        let httpMock: HttpTestingController;
        let elemDefault: INotification;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(NotificationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Notification(0, 0, TimeInterval.DAYS, currentDate, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        beforeDate: currentDate.format(DATE_FORMAT),
                        afterDate: currentDate.format(DATE_FORMAT),
                        sendDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Notification', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        beforeDate: currentDate.format(DATE_FORMAT),
                        afterDate: currentDate.format(DATE_FORMAT),
                        sendDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        beforeDate: currentDate,
                        afterDate: currentDate,
                        sendDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Notification(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Notification', async () => {
                const returnedFromService = Object.assign(
                    {
                        value: 1,
                        interval: 'BBBBBB',
                        beforeDate: currentDate.format(DATE_FORMAT),
                        afterDate: currentDate.format(DATE_FORMAT),
                        sendDate: currentDate.format(DATE_FORMAT),
                        sender: 'BBBBBB',
                        message: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        beforeDate: currentDate,
                        afterDate: currentDate,
                        sendDate: currentDate
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

            it('should return a list of Notification', async () => {
                const returnedFromService = Object.assign(
                    {
                        value: 1,
                        interval: 'BBBBBB',
                        beforeDate: currentDate.format(DATE_FORMAT),
                        afterDate: currentDate.format(DATE_FORMAT),
                        sendDate: currentDate.format(DATE_FORMAT),
                        sender: 'BBBBBB',
                        message: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        beforeDate: currentDate,
                        afterDate: currentDate,
                        sendDate: currentDate
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

            it('should delete a Notification', async () => {
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
