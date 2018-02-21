/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { PatternColumnGrvComponent } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.component';
import { PatternColumnGrvService } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.service';
import { PatternColumnGrv } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.model';

describe('Component Tests', () => {

    describe('PatternColumnGrv Management Component', () => {
        let comp: PatternColumnGrvComponent;
        let fixture: ComponentFixture<PatternColumnGrvComponent>;
        let service: PatternColumnGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [PatternColumnGrvComponent],
                providers: [
                    PatternColumnGrvService
                ]
            })
            .overrideTemplate(PatternColumnGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatternColumnGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatternColumnGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PatternColumnGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.patternColumns[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
