import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PatternGrv } from './pattern-grv.model';
import { PatternGrvService } from './pattern-grv.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-pattern-grv',
    templateUrl: './pattern-grv.component.html'
})
export class PatternGrvComponent implements OnInit, OnDestroy {
patterns: PatternGrv[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private patternService: PatternGrvService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.patternService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<PatternGrv[]>) => this.patterns = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.patternService.query().subscribe(
            (res: HttpResponse<PatternGrv[]>) => {
                this.patterns = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPatterns();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PatternGrv) {
        return item.id;
    }
    registerChangeInPatterns() {
        this.eventSubscriber = this.eventManager.subscribe('patternListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
