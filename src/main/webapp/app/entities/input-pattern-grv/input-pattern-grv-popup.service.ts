import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {InputPatternGrv} from './input-pattern-grv.model';
import {InputPatternGrvService} from './input-pattern-grv.service';

@Injectable()
export class InputPatternGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inputPatternService: InputPatternGrvService

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
                this.inputPatternService.find(id)
                    .subscribe((inputPatternResponse: HttpResponse<InputPatternGrv>) => {
                        const inputPattern: InputPatternGrv = inputPatternResponse.body;
                        this.ngbModalRef = this.inputPatternModalRef(component, inputPattern);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inputPatternModalRef(component, new InputPatternGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inputPatternModalRef(component: Component, inputPattern: InputPatternGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inputPattern = inputPattern;
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
