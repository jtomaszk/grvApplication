import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Error e2e test', () => {

    let navBarPage: NavBarPage;
    let errorDialogPage: ErrorDialogPage;
    let errorComponentsPage: ErrorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Errors', () => {
        navBarPage.goToEntity('error-grv');
        errorComponentsPage = new ErrorComponentsPage();
        expect(errorComponentsPage.getTitle())
            .toMatch(/Errors/);

    });

    it('should load create Error dialog', () => {
        errorComponentsPage.clickOnCreateButton();
        errorDialogPage = new ErrorDialogPage();
        expect(errorDialogPage.getModalTitle())
            .toMatch(/Create or edit a Error/);
        errorDialogPage.close();
    });

    it('should create and save Errors', () => {
        errorComponentsPage.clickOnCreateButton();
        errorDialogPage.setTitleInput('title');
        expect(errorDialogPage.getTitleInput()).toMatch('title');
        errorDialogPage.setMsgInput('msg');
        expect(errorDialogPage.getMsgInput()).toMatch('msg');
        errorDialogPage.setCreatedDateInput(12310020012301);
        expect(errorDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        errorDialogPage.sourceSelectLastOption();
        errorDialogPage.itemSelectLastOption();
        errorDialogPage.save();
        expect(errorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ErrorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-error-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ErrorDialogPage {
    modalTitle = element(by.css('h4#myErrorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    msgInput = element(by.css('textarea#field_msg'));
    createdDateInput = element(by.css('input#field_createdDate'));
    sourceSelect = element(by.css('select#field_source'));
    itemSelect = element(by.css('select#field_item'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setMsgInput = function(msg) {
        this.msgInput.sendKeys(msg);
    };

    getMsgInput = function() {
        return this.msgInput.getAttribute('value');
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
