/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { SourceGrvDetailComponent } from '../../../../../../main/webapp/app/entities/source-grv/source-grv-detail.component';
import { SourceGrvService } from '../../../../../../main/webapp/app/entities/source-grv/source-grv.service';
import { SourceGrv } from '../../../../../../main/webapp/app/entities/source-grv/source-grv.model';

describe('Component Tests', () => {

    describe('SourceGrv Management Detail Component', () => {
        let comp: SourceGrvDetailComponent;
        let fixture: ComponentFixture<SourceGrvDetailComponent>;
        let service: SourceGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [SourceGrvDetailComponent],
                providers: [
                    SourceGrvService
                ]
            })
            .overrideTemplate(SourceGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourceGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SourceGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.source).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
