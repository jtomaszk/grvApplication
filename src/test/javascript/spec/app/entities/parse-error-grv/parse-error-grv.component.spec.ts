/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { ParseErrorGrvComponent } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.component';
import { ParseErrorGrvService } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.service';
import { ParseErrorGrv } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.model';

describe('Component Tests', () => {

    describe('ParseErrorGrv Management Component', () => {
        let comp: ParseErrorGrvComponent;
        let fixture: ComponentFixture<ParseErrorGrvComponent>;
        let service: ParseErrorGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ParseErrorGrvComponent],
                providers: [
                    ParseErrorGrvService
                ]
            })
            .overrideTemplate(ParseErrorGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParseErrorGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParseErrorGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ParseErrorGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.parseErrors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
