/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { AreaGrvComponent } from '../../../../../../main/webapp/app/entities/area-grv/area-grv.component';
import { AreaGrvService } from '../../../../../../main/webapp/app/entities/area-grv/area-grv.service';
import { AreaGrv } from '../../../../../../main/webapp/app/entities/area-grv/area-grv.model';

describe('Component Tests', () => {

    describe('AreaGrv Management Component', () => {
        let comp: AreaGrvComponent;
        let fixture: ComponentFixture<AreaGrvComponent>;
        let service: AreaGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [AreaGrvComponent],
                providers: [
                    AreaGrvService
                ]
            })
            .overrideTemplate(AreaGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AreaGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AreaGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AreaGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.areas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
