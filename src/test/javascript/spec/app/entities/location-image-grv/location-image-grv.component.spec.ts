/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { LocationImageGrvComponent } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv.component';
import { LocationImageGrvService } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv.service';
import { LocationImageGrv } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv.model';

describe('Component Tests', () => {

    describe('LocationImageGrv Management Component', () => {
        let comp: LocationImageGrvComponent;
        let fixture: ComponentFixture<LocationImageGrvComponent>;
        let service: LocationImageGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [LocationImageGrvComponent],
                providers: [
                    LocationImageGrvService
                ]
            })
            .overrideTemplate(LocationImageGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationImageGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationImageGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LocationImageGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.locationImages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
