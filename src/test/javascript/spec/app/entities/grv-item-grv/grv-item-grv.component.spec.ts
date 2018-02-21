/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemGrvComponent } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.component';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.service';
import { GrvItemGrv } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.model';

describe('Component Tests', () => {

    describe('GrvItemGrv Management Component', () => {
        let comp: GrvItemGrvComponent;
        let fixture: ComponentFixture<GrvItemGrvComponent>;
        let service: GrvItemGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemGrvComponent],
                providers: [
                    GrvItemGrvService
                ]
            })
            .overrideTemplate(GrvItemGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GrvItemGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.grvItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
