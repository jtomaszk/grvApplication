/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemPersonGrvComponent } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.component';
import { GrvItemPersonGrvService } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.service';
import { GrvItemPersonGrv } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.model';

describe('Component Tests', () => {

    describe('GrvItemPersonGrv Management Component', () => {
        let comp: GrvItemPersonGrvComponent;
        let fixture: ComponentFixture<GrvItemPersonGrvComponent>;
        let service: GrvItemPersonGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemPersonGrvComponent],
                providers: [
                    GrvItemPersonGrvService
                ]
            })
            .overrideTemplate(GrvItemPersonGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemPersonGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemPersonGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GrvItemPersonGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.grvItemPeople[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
