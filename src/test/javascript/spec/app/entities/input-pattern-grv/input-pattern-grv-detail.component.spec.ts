/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { InputPatternGrvDetailComponent } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv-detail.component';
import { InputPatternGrvService } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv.service';
import { InputPatternGrv } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv.model';

describe('Component Tests', () => {

    describe('InputPatternGrv Management Detail Component', () => {
        let comp: InputPatternGrvDetailComponent;
        let fixture: ComponentFixture<InputPatternGrvDetailComponent>;
        let service: InputPatternGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [InputPatternGrvDetailComponent],
                providers: [
                    InputPatternGrvService
                ]
            })
            .overrideTemplate(InputPatternGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InputPatternGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InputPatternGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InputPatternGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.inputPattern).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
