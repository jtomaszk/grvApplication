/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { ParseErrorGrvDetailComponent } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv-detail.component';
import { ParseErrorGrvService } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.service';
import { ParseErrorGrv } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.model';

describe('Component Tests', () => {

    describe('ParseErrorGrv Management Detail Component', () => {
        let comp: ParseErrorGrvDetailComponent;
        let fixture: ComponentFixture<ParseErrorGrvDetailComponent>;
        let service: ParseErrorGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ParseErrorGrvDetailComponent],
                providers: [
                    ParseErrorGrvService
                ]
            })
            .overrideTemplate(ParseErrorGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParseErrorGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParseErrorGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ParseErrorGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.parseError).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
