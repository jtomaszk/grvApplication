import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {DatePipe} from '@angular/common';
import {ParseErrorGrv} from './parse-error-grv.model';
import {ParseErrorGrvService} from './parse-error-grv.service';

@Injectable()
export class ParseErrorGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private parseErrorService: ParseErrorGrvService

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
                this.parseErrorService.find(id)
                    .subscribe((parseErrorResponse: HttpResponse<ParseErrorGrv>) => {
                        const parseError: ParseErrorGrv = parseErrorResponse.body;
                        parseError.createdDate = this.datePipe
                            .transform(parseError.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.parseErrorModalRef(component, parseError);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.parseErrorModalRef(component, new ParseErrorGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    parseErrorModalRef(component: Component, parseError: ParseErrorGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.parseError = parseError;
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
