/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { InputPatternGrvComponent } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv.component';
import { InputPatternGrvService } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv.service';
import { InputPatternGrv } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv.model';

describe('Component Tests', () => {

    describe('InputPatternGrv Management Component', () => {
        let comp: InputPatternGrvComponent;
        let fixture: ComponentFixture<InputPatternGrvComponent>;
        let service: InputPatternGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [InputPatternGrvComponent],
                providers: [
                    InputPatternGrvService
                ]
            })
            .overrideTemplate(InputPatternGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InputPatternGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InputPatternGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InputPatternGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.inputPatterns[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
