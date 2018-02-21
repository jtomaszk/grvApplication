/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { LocationGrvComponent } from '../../../../../../main/webapp/app/entities/location-grv/location-grv.component';
import { LocationGrvService } from '../../../../../../main/webapp/app/entities/location-grv/location-grv.service';
import { LocationGrv } from '../../../../../../main/webapp/app/entities/location-grv/location-grv.model';

describe('Component Tests', () => {

    describe('LocationGrv Management Component', () => {
        let comp: LocationGrvComponent;
        let fixture: ComponentFixture<LocationGrvComponent>;
        let service: LocationGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [LocationGrvComponent],
                providers: [
                    LocationGrvService
                ]
            })
            .overrideTemplate(LocationGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LocationGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.locations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
