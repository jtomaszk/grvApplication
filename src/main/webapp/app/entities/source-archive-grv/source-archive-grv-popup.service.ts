import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {DatePipe} from '@angular/common';
import {SourceArchiveGrv} from './source-archive-grv.model';
import {SourceArchiveGrvService} from './source-archive-grv.service';

@Injectable()
export class SourceArchiveGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private sourceArchiveService: SourceArchiveGrvService

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
                this.sourceArchiveService.find(id)
                    .subscribe((sourceArchiveResponse: HttpResponse<SourceArchiveGrv>) => {
                        const sourceArchive: SourceArchiveGrv = sourceArchiveResponse.body;
                        sourceArchive.createdDate = this.datePipe
                            .transform(sourceArchive.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.sourceArchiveModalRef(component, sourceArchive);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sourceArchiveModalRef(component, new SourceArchiveGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sourceArchiveModalRef(component: Component, sourceArchive: SourceArchiveGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sourceArchive = sourceArchive;
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
