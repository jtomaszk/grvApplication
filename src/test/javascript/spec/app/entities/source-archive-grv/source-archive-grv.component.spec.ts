/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { SourceArchiveGrvComponent } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.component';
import { SourceArchiveGrvService } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.service';
import { SourceArchiveGrv } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.model';

describe('Component Tests', () => {

    describe('SourceArchiveGrv Management Component', () => {
        let comp: SourceArchiveGrvComponent;
        let fixture: ComponentFixture<SourceArchiveGrvComponent>;
        let service: SourceArchiveGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [SourceArchiveGrvComponent],
                providers: [
                    SourceArchiveGrvService
                ]
            })
            .overrideTemplate(SourceArchiveGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourceArchiveGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceArchiveGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SourceArchiveGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sourceArchives[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
