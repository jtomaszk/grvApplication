import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {PatternColumnGrv} from './pattern-column-grv.model';
import {PatternColumnGrvService} from './pattern-column-grv.service';

@Injectable()
export class PatternColumnGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private patternColumnService: PatternColumnGrvService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.patternColumnService.find(id)
                    .subscribe((patternColumnResponse: HttpResponse<PatternColumnGrv>) => {
                        const patternColumn: PatternColumnGrv = patternColumnResponse.body;
                        this.ngbModalRef = this.patternColumnModalRef(component, patternColumn);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.patternColumnModalRef(component, new PatternColumnGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    patternColumnModalRef(component: Component, patternColumn: PatternColumnGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.patternColumn = patternColumn;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
