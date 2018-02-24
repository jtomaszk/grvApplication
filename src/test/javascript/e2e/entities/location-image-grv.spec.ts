import {browser, by, element} from 'protractor';
import {NavBarPage} from '../page-objects/jhi-page-objects';

describe('LocationImage e2e test', () => {

    let navBarPage: NavBarPage;
    let locationImageDialogPage: LocationImageDialogPage;
    let locationImageComponentsPage: LocationImageComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load LocationImages', () => {
        navBarPage.goToEntity('location-image-grv');
        locationImageComponentsPage = new LocationImageComponentsPage();
        expect(locationImageComponentsPage.getTitle())
            .toMatch(/Location Images/);

    });

    it('should load create LocationImage dialog', () => {
        locationImageComponentsPage.clickOnCreateButton();
        locationImageDialogPage = new LocationImageDialogPage();
        expect(locationImageDialogPage.getModalTitle())
            .toMatch(/Create or edit a Location Image/);
        locationImageDialogPage.close();
    });

   /* it('should create and save LocationImages', () => {
        locationImageComponentsPage.clickOnCreateButton();
        locationImageDialogPage.setCreatedDateInput(12310020012301);
        expect(locationImageDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        locationImageDialogPage.setImageInput(absolutePath);
        locationImageDialogPage.locationSelectLastOption();
        locationImageDialogPage.save();
        expect(locationImageDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LocationImageComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-location-image-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class LocationImageDialogPage {
    modalTitle = element(by.css('h4#myLocationImageLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    createdDateInput = element(by.css('input#field_createdDate'));
    imageInput = element(by.css('input#file_image'));
    locationSelect = element(by.css('select#field_location'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCreatedDateInput = function(createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    };

    getCreatedDateInput = function() {
        return this.createdDateInput.getAttribute('value');
    };

    setImageInput = function(image) {
        this.imageInput.sendKeys(image);
    };

    getImageInput = function() {
        return this.imageInput.getAttribute('value');
    };

    locationSelectLastOption = function() {
        this.locationSelect.all(by.tagName('option')).last().click();
    };

    locationSelectOption = function(option) {
        this.locationSelect.sendKeys(option);
    };

    getLocationSelect = function() {
        return this.locationSelect;
    };

    getLocationSelectedOption = function() {
        return this.locationSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
