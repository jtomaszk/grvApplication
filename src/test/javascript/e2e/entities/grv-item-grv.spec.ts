import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('GrvItem e2e test', () => {

    let navBarPage: NavBarPage;
    let grvItemDialogPage: GrvItemDialogPage;
    let grvItemComponentsPage: GrvItemComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GrvItems', () => {
        navBarPage.goToEntity('grv-item-grv');
        grvItemComponentsPage = new GrvItemComponentsPage();
        expect(grvItemComponentsPage.getTitle())
            .toMatch(/Grv Items/);

    });

    it('should load create GrvItem dialog', () => {
        grvItemComponentsPage.clickOnCreateButton();
        grvItemDialogPage = new GrvItemDialogPage();
        expect(grvItemDialogPage.getModalTitle())
            .toMatch(/Create or edit a Grv Item/);
        grvItemDialogPage.close();
    });

   /* it('should create and save GrvItems', () => {
        grvItemComponentsPage.clickOnCreateButton();
        grvItemDialogPage.setFirstNameInput('firstName');
        expect(grvItemDialogPage.getFirstNameInput()).toMatch('firstName');
        grvItemDialogPage.setLastNameInput('lastName');
        expect(grvItemDialogPage.getLastNameInput()).toMatch('lastName');
        grvItemDialogPage.setAnotherLastNameInput('anotherLastName');
        expect(grvItemDialogPage.getAnotherLastNameInput()).toMatch('anotherLastName');
        grvItemDialogPage.setStartDateStringInput('startDateString');
        expect(grvItemDialogPage.getStartDateStringInput()).toMatch('startDateString');
        grvItemDialogPage.setStartDateInput(12310020012301);
        expect(grvItemDialogPage.getStartDateInput()).toMatch('2001-12-31T02:30');
        grvItemDialogPage.setEndDateStringInput('endDateString');
        expect(grvItemDialogPage.getEndDateStringInput()).toMatch('endDateString');
        grvItemDialogPage.setEndDateInput(12310020012301);
        expect(grvItemDialogPage.getEndDateInput()).toMatch('2001-12-31T02:30');
        grvItemDialogPage.setValidToDateStringInput('validToDateString');
        expect(grvItemDialogPage.getValidToDateStringInput()).toMatch('validToDateString');
        grvItemDialogPage.setValidToDateInput(12310020012301);
        expect(grvItemDialogPage.getValidToDateInput()).toMatch('2001-12-31T02:30');
        grvItemDialogPage.setExternalidInput('externalid');
        expect(grvItemDialogPage.getExternalidInput()).toMatch('externalid');
        grvItemDialogPage.setInfoInput('info');
        expect(grvItemDialogPage.getInfoInput()).toMatch('info');
        grvItemDialogPage.setDocnrInput('docnr');
        expect(grvItemDialogPage.getDocnrInput()).toMatch('docnr');
        grvItemDialogPage.setCreatedDateInput(12310020012301);
        expect(grvItemDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        grvItemDialogPage.sourceSelectLastOption();
        grvItemDialogPage.locationSelectLastOption();
        grvItemDialogPage.save();
        expect(grvItemDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class GrvItemComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-grv-item-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class GrvItemDialogPage {
    modalTitle = element(by.css('h4#myGrvItemLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    anotherLastNameInput = element(by.css('input#field_anotherLastName'));
    startDateStringInput = element(by.css('input#field_startDateString'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateStringInput = element(by.css('input#field_endDateString'));
    endDateInput = element(by.css('input#field_endDate'));
    validToDateStringInput = element(by.css('input#field_validToDateString'));
    validToDateInput = element(by.css('input#field_validToDate'));
    externalidInput = element(by.css('input#field_externalid'));
    infoInput = element(by.css('input#field_info'));
    docnrInput = element(by.css('input#field_docnr'));
    createdDateInput = element(by.css('input#field_createdDate'));
    sourceSelect = element(by.css('select#field_source'));
    locationSelect = element(by.css('select#field_location'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setFirstNameInput = function(firstName) {
        this.firstNameInput.sendKeys(firstName);
    };

    getFirstNameInput = function() {
        return this.firstNameInput.getAttribute('value');
    };

    setLastNameInput = function(lastName) {
        this.lastNameInput.sendKeys(lastName);
    };

    getLastNameInput = function() {
        return this.lastNameInput.getAttribute('value');
    };

    setAnotherLastNameInput = function(anotherLastName) {
        this.anotherLastNameInput.sendKeys(anotherLastName);
    };

    getAnotherLastNameInput = function() {
        return this.anotherLastNameInput.getAttribute('value');
    };

    setStartDateStringInput = function(startDateString) {
        this.startDateStringInput.sendKeys(startDateString);
    };

    getStartDateStringInput = function() {
        return this.startDateStringInput.getAttribute('value');
    };

    setStartDateInput = function(startDate) {
        this.startDateInput.sendKeys(startDate);
    };

    getStartDateInput = function() {
        return this.startDateInput.getAttribute('value');
    };

    setEndDateStringInput = function(endDateString) {
        this.endDateStringInput.sendKeys(endDateString);
    };

    getEndDateStringInput = function() {
        return this.endDateStringInput.getAttribute('value');
    };

    setEndDateInput = function(endDate) {
        this.endDateInput.sendKeys(endDate);
    };

    getEndDateInput = function() {
        return this.endDateInput.getAttribute('value');
    };

    setValidToDateStringInput = function(validToDateString) {
        this.validToDateStringInput.sendKeys(validToDateString);
    };

    getValidToDateStringInput = function() {
        return this.validToDateStringInput.getAttribute('value');
    };

    setValidToDateInput = function(validToDate) {
        this.validToDateInput.sendKeys(validToDate);
    };

    getValidToDateInput = function() {
        return this.validToDateInput.getAttribute('value');
    };

    setExternalidInput = function(externalid) {
        this.externalidInput.sendKeys(externalid);
    };

    getExternalidInput = function() {
        return this.externalidInput.getAttribute('value');
    };

    setInfoInput = function(info) {
        this.infoInput.sendKeys(info);
    };

    getInfoInput = function() {
        return this.infoInput.getAttribute('value');
    };

    setDocnrInput = function(docnr) {
        this.docnrInput.sendKeys(docnr);
    };

    getDocnrInput = function() {
        return this.docnrInput.getAttribute('value');
    };

    setCreatedDateInput = function(createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    };

    getCreatedDateInput = function() {
        return this.createdDateInput.getAttribute('value');
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
