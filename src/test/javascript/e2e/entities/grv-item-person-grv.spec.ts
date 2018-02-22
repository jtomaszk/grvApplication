import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('GrvItemPerson e2e test', () => {

    let navBarPage: NavBarPage;
    let grvItemPersonDialogPage: GrvItemPersonDialogPage;
    let grvItemPersonComponentsPage: GrvItemPersonComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GrvItemPeople', () => {
        navBarPage.goToEntity('grv-item-person-grv');
        grvItemPersonComponentsPage = new GrvItemPersonComponentsPage();
        expect(grvItemPersonComponentsPage.getTitle())
            .toMatch(/Grv Item People/);

    });

    it('should load create GrvItemPerson dialog', () => {
        grvItemPersonComponentsPage.clickOnCreateButton();
        grvItemPersonDialogPage = new GrvItemPersonDialogPage();
        expect(grvItemPersonDialogPage.getModalTitle())
            .toMatch(/Create or edit a Grv Item Person/);
        grvItemPersonDialogPage.close();
    });

    it('should create and save GrvItemPeople', () => {
        grvItemPersonComponentsPage.clickOnCreateButton();
        grvItemPersonDialogPage.setFirstNameInput('firstName');
        expect(grvItemPersonDialogPage.getFirstNameInput()).toMatch('firstName');
        grvItemPersonDialogPage.setLastNameInput('lastName');
        expect(grvItemPersonDialogPage.getLastNameInput()).toMatch('lastName');
        grvItemPersonDialogPage.setAnotherLastNameInput('anotherLastName');
        expect(grvItemPersonDialogPage.getAnotherLastNameInput()).toMatch('anotherLastName');
        grvItemPersonDialogPage.setStartDateStringInput('startDateString');
        expect(grvItemPersonDialogPage.getStartDateStringInput()).toMatch('startDateString');
        grvItemPersonDialogPage.setEndDateStringInput('endDateString');
        expect(grvItemPersonDialogPage.getEndDateStringInput()).toMatch('endDateString');
        grvItemPersonDialogPage.itemSelectLastOption();
        grvItemPersonDialogPage.save();
        expect(grvItemPersonDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class GrvItemPersonComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-grv-item-person-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class GrvItemPersonDialogPage {
    modalTitle = element(by.css('h4#myGrvItemPersonLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    anotherLastNameInput = element(by.css('input#field_anotherLastName'));
    startDateStringInput = element(by.css('input#field_startDateString'));
    endDateStringInput = element(by.css('input#field_endDateString'));
    itemSelect = element(by.css('select#field_item'));

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

    setEndDateStringInput = function(endDateString) {
        this.endDateStringInput.sendKeys(endDateString);
    };

    getEndDateStringInput = function() {
        return this.endDateStringInput.getAttribute('value');
    };

    itemSelectLastOption = function() {
        this.itemSelect.all(by.tagName('option')).last().click();
    };

    itemSelectOption = function(option) {
        this.itemSelect.sendKeys(option);
    };

    getItemSelect = function() {
        return this.itemSelect;
    };

    getItemSelectedOption = function() {
        return this.itemSelect.element(by.css('option:checked')).getText();
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
