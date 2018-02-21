/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { PatternGrvComponent } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.component';
import { PatternGrvService } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.service';
import { PatternGrv } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.model';

describe('Component Tests', () => {

    describe('PatternGrv Management Component', () => {
        let comp: PatternGrvComponent;
        let fixture: ComponentFixture<PatternGrvComponent>;
        let service: PatternGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [PatternGrvComponent],
                providers: [
                    PatternGrvService
                ]
            })
            .overrideTemplate(PatternGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatternGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatternGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PatternGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.patterns[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
