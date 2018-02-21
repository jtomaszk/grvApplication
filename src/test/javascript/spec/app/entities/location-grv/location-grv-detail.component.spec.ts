/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { LocationGrvDetailComponent } from '../../../../../../main/webapp/app/entities/location-grv/location-grv-detail.component';
import { LocationGrvService } from '../../../../../../main/webapp/app/entities/location-grv/location-grv.service';
import { LocationGrv } from '../../../../../../main/webapp/app/entities/location-grv/location-grv.model';

describe('Component Tests', () => {

    describe('LocationGrv Management Detail Component', () => {
        let comp: LocationGrvDetailComponent;
        let fixture: ComponentFixture<LocationGrvDetailComponent>;
        let service: LocationGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [LocationGrvDetailComponent],
                providers: [
                    LocationGrvService
                ]
            })
            .overrideTemplate(LocationGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LocationGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.location).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
