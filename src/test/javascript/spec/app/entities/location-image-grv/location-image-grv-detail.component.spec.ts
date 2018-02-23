/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { LocationImageGrvDetailComponent } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv-detail.component';
import { LocationImageGrvService } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv.service';
import { LocationImageGrv } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv.model';

describe('Component Tests', () => {

    describe('LocationImageGrv Management Detail Component', () => {
        let comp: LocationImageGrvDetailComponent;
        let fixture: ComponentFixture<LocationImageGrvDetailComponent>;
        let service: LocationImageGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [LocationImageGrvDetailComponent],
                providers: [
                    LocationImageGrvService
                ]
            })
            .overrideTemplate(LocationImageGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationImageGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationImageGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LocationImageGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.locationImage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
