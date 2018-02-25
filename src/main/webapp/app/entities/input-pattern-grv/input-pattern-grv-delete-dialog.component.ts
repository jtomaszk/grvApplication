import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InputPatternGrv } from './input-pattern-grv.model';
import { InputPatternGrvPopupService } from './input-pattern-grv-popup.service';
import { InputPatternGrvService } from './input-pattern-grv.service';

@Component({
    selector: 'jhi-input-pattern-grv-delete-dialog',
    templateUrl: './input-pattern-grv-delete-dialog.component.html'
})
export class InputPatternGrvDeleteDialogComponent {

    inputPattern: InputPatternGrv;

    constructor(
        private inputPatternService: InputPatternGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inputPatternService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inputPatternListModification',
                content: 'Deleted an inputPattern'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-input-pattern-grv-delete-popup',
    template: ''
})
export class InputPatternGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inputPatternPopupService: InputPatternGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inputPatternPopupService
                .open(InputPatternGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
