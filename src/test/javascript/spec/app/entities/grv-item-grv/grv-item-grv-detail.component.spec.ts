/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemGrvDetailComponent } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv-detail.component';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.service';
import { GrvItemGrv } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.model';

describe('Component Tests', () => {

    describe('GrvItemGrv Management Detail Component', () => {
        let comp: GrvItemGrvDetailComponent;
        let fixture: ComponentFixture<GrvItemGrvDetailComponent>;
        let service: GrvItemGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemGrvDetailComponent],
                providers: [
                    GrvItemGrvService
                ]
            })
            .overrideTemplate(GrvItemGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new GrvItemGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.grvItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
