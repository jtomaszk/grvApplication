import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';
import {DatePipe} from '@angular/common';
import {LocationImageGrv} from './location-image-grv.model';
import {LocationImageGrvService} from './location-image-grv.service';

@Injectable()
export class LocationImageGrvPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private locationImageService: LocationImageGrvService

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
                this.locationImageService.find(id)
                    .subscribe((locationImageResponse: HttpResponse<LocationImageGrv>) => {
                        const locationImage: LocationImageGrv = locationImageResponse.body;
                        locationImage.createdDate = this.datePipe
                            .transform(locationImage.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.locationImageModalRef(component, locationImage);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.locationImageModalRef(component, new LocationImageGrv());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    locationImageModalRef(component: Component, locationImage: LocationImageGrv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.locationImage = locationImage;
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
