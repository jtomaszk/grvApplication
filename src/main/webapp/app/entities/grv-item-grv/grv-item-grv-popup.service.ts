import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {DatePipe} from '@angular/common';
import {GrvItemGrv} from './grv-item-grv.model';
import {GrvItemGrvService} from './grv-item-grv.service';

@Injectable()
export class GrvItemGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private grvItemService: GrvItemGrvService

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
                this.grvItemService.find(id)
                    .subscribe((grvItemResponse: HttpResponse<GrvItemGrv>) => {
                        const grvItem: GrvItemGrv = grvItemResponse.body;
                        grvItem.startDate = this.datePipe
                            .transform(grvItem.startDate, 'yyyy-MM-ddTHH:mm:ss');
                        grvItem.endDate = this.datePipe
                            .transform(grvItem.endDate, 'yyyy-MM-ddTHH:mm:ss');
                        grvItem.validToDate = this.datePipe
                            .transform(grvItem.validToDate, 'yyyy-MM-ddTHH:mm:ss');
                        grvItem.createdDate = this.datePipe
                            .transform(grvItem.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.grvItemModalRef(component, grvItem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.grvItemModalRef(component, new GrvItemGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    grvItemModalRef(component: Component, grvItem: GrvItemGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.grvItem = grvItem;
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
