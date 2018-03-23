import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {ParseErrorGrv} from './parse-error-grv.model';
import {ParseErrorGrvPopupService} from './parse-error-grv-popup.service';
import {ParseErrorGrvService} from './parse-error-grv.service';

@Component({
    selector: 'jhi-parse-error-grv-delete-dialog',
    templateUrl: './parse-error-grv-delete-dialog.component.html'
})
export class ParseErrorGrvDeleteDialogComponent {

    parseError: ParseErrorGrv;

    constructor(
        private parseErrorService: ParseErrorGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parseErrorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parseErrorListModification',
                content: 'Deleted an parseError'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parse-error-grv-delete-popup',
    template: ''
})
export class ParseErrorGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parseErrorPopupService: ParseErrorGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parseErrorPopupService
                .open(ParseErrorGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
