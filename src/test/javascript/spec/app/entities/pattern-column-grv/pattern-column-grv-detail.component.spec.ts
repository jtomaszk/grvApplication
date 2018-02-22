/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { PatternColumnGrvDetailComponent } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv-detail.component';
import { PatternColumnGrvService } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.service';
import { PatternColumnGrv } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.model';

describe('Component Tests', () => {

    describe('PatternColumnGrv Management Detail Component', () => {
        let comp: PatternColumnGrvDetailComponent;
        let fixture: ComponentFixture<PatternColumnGrvDetailComponent>;
        let service: PatternColumnGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [PatternColumnGrvDetailComponent],
                providers: [
                    PatternColumnGrvService
                ]
            })
            .overrideTemplate(PatternColumnGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatternColumnGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatternColumnGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PatternColumnGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.patternColumn).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
