/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { ErrorGrvDetailComponent } from '../../../../../../main/webapp/app/entities/error-grv/error-grv-detail.component';
import { ErrorGrvService } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.service';
import { ErrorGrv } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.model';

describe('Component Tests', () => {

    describe('ErrorGrv Management Detail Component', () => {
        let comp: ErrorGrvDetailComponent;
        let fixture: ComponentFixture<ErrorGrvDetailComponent>;
        let service: ErrorGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ErrorGrvDetailComponent],
                providers: [
                    ErrorGrvService
                ]
            })
            .overrideTemplate(ErrorGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ErrorGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ErrorGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ErrorGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.error).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
