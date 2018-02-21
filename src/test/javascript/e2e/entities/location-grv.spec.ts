import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Location e2e test', () => {

    let navBarPage: NavBarPage;
    let locationDialogPage: LocationDialogPage;
    let locationComponentsPage: LocationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Locations', () => {
        navBarPage.goToEntity('location-grv');
        locationComponentsPage = new LocationComponentsPage();
        expect(locationComponentsPage.getTitle())
            .toMatch(/Locations/);

    });

    it('should load create Location dialog', () => {
        locationComponentsPage.clickOnCreateButton();
        locationDialogPage = new LocationDialogPage();
        expect(locationDialogPage.getModalTitle())
            .toMatch(/Create or edit a Location/);
        locationDialogPage.close();
    });

   /* it('should create and save Locations', () => {
        locationComponentsPage.clickOnCreateButton();
        locationDialogPage.setExternalidInput('externalid');
        expect(locationDialogPage.getExternalidInput()).toMatch('externalid');
        locationDialogPage.setCreatedDateInput(12310020012301);
        expect(locationDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        locationDialogPage.setCoordsInput('coords');
        expect(locationDialogPage.getCoordsInput()).toMatch('coords');
        locationDialogPage.sourceSelectLastOption();
        locationDialogPage.save();
        expect(locationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LocationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-location-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class LocationDialogPage {
    modalTitle = element(by.css('h4#myLocationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    externalidInput = element(by.css('input#field_externalid'));
    createdDateInput = element(by.css('input#field_createdDate'));
    coordsInput = element(by.css('input#field_coords'));
    sourceSelect = element(by.css('select#field_source'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setExternalidInput = function(externalid) {
        this.externalidInput.sendKeys(externalid);
    };

    getExternalidInput = function() {
        return this.externalidInput.getAttribute('value');
    };

    setCreatedDateInput = function(createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    };

    getCreatedDateInput = function() {
        return this.createdDateInput.getAttribute('value');
    };

    setCoordsInput = function(coords) {
        this.coordsInput.sendKeys(coords);
    };

    getCoordsInput = function() {
        return this.coordsInput.getAttribute('value');
    };

    sourceSelectLastOption = function() {
        this.sourceSelect.all(by.tagName('option')).last().click();
    };

    sourceSelectOption = function(option) {
        this.sourceSelect.sendKeys(option);
    };

    getSourceSelect = function() {
        return this.sourceSelect;
    };

    getSourceSelectedOption = function() {
        return this.sourceSelect.element(by.css('option:checked')).getText();
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
