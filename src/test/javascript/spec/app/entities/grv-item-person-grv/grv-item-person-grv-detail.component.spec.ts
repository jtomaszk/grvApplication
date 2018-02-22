/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemPersonGrvDetailComponent } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv-detail.component';
import { GrvItemPersonGrvService } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.service';
import { GrvItemPersonGrv } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.model';

describe('Component Tests', () => {

    describe('GrvItemPersonGrv Management Detail Component', () => {
        let comp: GrvItemPersonGrvDetailComponent;
        let fixture: ComponentFixture<GrvItemPersonGrvDetailComponent>;
        let service: GrvItemPersonGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemPersonGrvDetailComponent],
                providers: [
                    GrvItemPersonGrvService
                ]
            })
            .overrideTemplate(GrvItemPersonGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemPersonGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemPersonGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new GrvItemPersonGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.grvItemPerson).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
