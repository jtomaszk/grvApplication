import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {SourceArchiveGrv} from './source-archive-grv.model';
import {SourceArchiveGrvService} from './source-archive-grv.service';

@Component({
    selector: 'jhi-source-archive-grv-detail',
    templateUrl: './source-archive-grv-detail.component.html'
})
export class SourceArchiveGrvDetailComponent implements OnInit, OnDestroy {

    sourceArchive: SourceArchiveGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private sourceArchiveService: SourceArchiveGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSourceArchives();
    }

    load(id) {
        this.sourceArchiveService.find(id)
            .subscribe((sourceArchiveResponse: HttpResponse<SourceArchiveGrv>) => {
                this.sourceArchive = sourceArchiveResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSourceArchives() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sourceArchiveListModification',
            (response) => this.load(this.sourceArchive.id)
        );
    }
}
