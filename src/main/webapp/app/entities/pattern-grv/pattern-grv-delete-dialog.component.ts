import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PatternGrv } from './pattern-grv.model';
import { PatternGrvPopupService } from './pattern-grv-popup.service';
import { PatternGrvService } from './pattern-grv.service';

@Component({
    selector: 'jhi-pattern-grv-delete-dialog',
    templateUrl: './pattern-grv-delete-dialog.component.html'
})
export class PatternGrvDeleteDialogComponent {

    pattern: PatternGrv;

    constructor(
        private patternService: PatternGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.patternService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'patternListModification',
                content: 'Deleted an pattern'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pattern-grv-delete-popup',
    template: ''
})
export class PatternGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patternPopupService: PatternGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.patternPopupService
                .open(PatternGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
