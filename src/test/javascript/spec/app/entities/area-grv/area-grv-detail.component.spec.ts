/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { AreaGrvDetailComponent } from '../../../../../../main/webapp/app/entities/area-grv/area-grv-detail.component';
import { AreaGrvService } from '../../../../../../main/webapp/app/entities/area-grv/area-grv.service';
import { AreaGrv } from '../../../../../../main/webapp/app/entities/area-grv/area-grv.model';

describe('Component Tests', () => {

    describe('AreaGrv Management Detail Component', () => {
        let comp: AreaGrvDetailComponent;
        let fixture: ComponentFixture<AreaGrvDetailComponent>;
        let service: AreaGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [AreaGrvDetailComponent],
                providers: [
                    AreaGrvService
                ]
            })
            .overrideTemplate(AreaGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AreaGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AreaGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AreaGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.area).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
