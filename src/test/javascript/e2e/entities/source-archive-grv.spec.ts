import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SourceArchive e2e test', () => {

    let navBarPage: NavBarPage;
    let sourceArchiveDialogPage: SourceArchiveDialogPage;
    let sourceArchiveComponentsPage: SourceArchiveComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SourceArchives', () => {
        navBarPage.goToEntity('source-archive-grv');
        sourceArchiveComponentsPage = new SourceArchiveComponentsPage();
        expect(sourceArchiveComponentsPage.getTitle())
            .toMatch(/Source Archives/);

    });

    it('should load create SourceArchive dialog', () => {
        sourceArchiveComponentsPage.clickOnCreateButton();
        sourceArchiveDialogPage = new SourceArchiveDialogPage();
        expect(sourceArchiveDialogPage.getModalTitle())
            .toMatch(/Create or edit a Source Archive/);
        sourceArchiveDialogPage.close();
    });

   /* it('should create and save SourceArchives', () => {
        sourceArchiveComponentsPage.clickOnCreateButton();
        sourceArchiveDialogPage.setCreatedDateInput(12310020012301);
        expect(sourceArchiveDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        sourceArchiveDialogPage.setJsonInput('json');
        expect(sourceArchiveDialogPage.getJsonInput()).toMatch('json');
        sourceArchiveDialogPage.sourceSelectLastOption();
        sourceArchiveDialogPage.save();
        expect(sourceArchiveDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SourceArchiveComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-source-archive-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SourceArchiveDialogPage {
    modalTitle = element(by.css('h4#mySourceArchiveLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    createdDateInput = element(by.css('input#field_createdDate'));
    jsonInput = element(by.css('textarea#field_json'));
    sourceSelect = element(by.css('select#field_source'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCreatedDateInput = function(createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    };

    getCreatedDateInput = function() {
        return this.createdDateInput.getAttribute('value');
    };

    setJsonInput = function(json) {
        this.jsonInput.sendKeys(json);
    };

    getJsonInput = function() {
        return this.jsonInput.getAttribute('value');
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
