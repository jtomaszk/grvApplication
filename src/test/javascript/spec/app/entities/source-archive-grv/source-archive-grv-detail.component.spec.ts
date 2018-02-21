/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GrvApplicationTestModule } from '../../../test.module';
import { SourceArchiveGrvDetailComponent } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv-detail.component';
import { SourceArchiveGrvService } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.service';
import { SourceArchiveGrv } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.model';

describe('Component Tests', () => {

    describe('SourceArchiveGrv Management Detail Component', () => {
        let comp: SourceArchiveGrvDetailComponent;
        let fixture: ComponentFixture<SourceArchiveGrvDetailComponent>;
        let service: SourceArchiveGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [SourceArchiveGrvDetailComponent],
                providers: [
                    SourceArchiveGrvService
                ]
            })
            .overrideTemplate(SourceArchiveGrvDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourceArchiveGrvDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceArchiveGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SourceArchiveGrv(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sourceArchive).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
