/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { PatternGrvDetailComponent } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv-detail.component';
import { PatternGrvService } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.service';
import { PatternGrv } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.model';

describe('Component Tests', () => {

    describe('PatternGrv Management Detail Component', () => {
        let comp: PatternGrvDetailComponent;
        let fixture: ComponentFixture<PatternGrvDetailComponent>;
        let service: PatternGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [PatternGrvDetailComponent],
                providers: [
                    PatternGrvService
                ]
            })
            .overrideTemplate(PatternGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatternGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatternGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PatternGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pattern).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
