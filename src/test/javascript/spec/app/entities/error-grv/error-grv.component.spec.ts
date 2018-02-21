/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { ErrorGrvComponent } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.component';
import { ErrorGrvService } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.service';
import { ErrorGrv } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.model';

describe('Component Tests', () => {

    describe('ErrorGrv Management Component', () => {
        let comp: ErrorGrvComponent;
        let fixture: ComponentFixture<ErrorGrvComponent>;
        let service: ErrorGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ErrorGrvComponent],
                providers: [
                    ErrorGrvService
                ]
            })
            .overrideTemplate(ErrorGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ErrorGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ErrorGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ErrorGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.errors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
