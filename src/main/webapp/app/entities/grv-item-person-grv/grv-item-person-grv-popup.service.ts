import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {GrvItemPersonGrv} from './grv-item-person-grv.model';
import {GrvItemPersonGrvService} from './grv-item-person-grv.service';

@Injectable()
export class GrvItemPersonGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private grvItemPersonService: GrvItemPersonGrvService

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
                this.grvItemPersonService.find(id)
                    .subscribe((grvItemPersonResponse: HttpResponse<GrvItemPersonGrv>) => {
                        const grvItemPerson: GrvItemPersonGrv = grvItemPersonResponse.body;
                        this.ngbModalRef = this.grvItemPersonModalRef(component, grvItemPerson);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.grvItemPersonModalRef(component, new GrvItemPersonGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    grvItemPersonModalRef(component: Component, grvItemPerson: GrvItemPersonGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.grvItemPerson = grvItemPerson;
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
