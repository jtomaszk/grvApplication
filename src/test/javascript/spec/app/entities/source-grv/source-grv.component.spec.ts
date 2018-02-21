/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrvApplicationTestModule } from '../../../test.module';
import { SourceGrvComponent } from '../../../../../../main/webapp/app/entities/source-grv/source-grv.component';
import { SourceGrvService } from '../../../../../../main/webapp/app/entities/source-grv/source-grv.service';
import { SourceGrv } from '../../../../../../main/webapp/app/entities/source-grv/source-grv.model';

describe('Component Tests', () => {

    describe('SourceGrv Management Component', () => {
        let comp: SourceGrvComponent;
        let fixture: ComponentFixture<SourceGrvComponent>;
        let service: SourceGrvService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [SourceGrvComponent],
                providers: [
                    SourceGrvService
                ]
            })
            .overrideTemplate(SourceGrvComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourceGrvComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceGrvService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SourceGrv(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sources[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
