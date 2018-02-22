import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Source e2e test', () => {

    let navBarPage: NavBarPage;
    let sourceDialogPage: SourceDialogPage;
    let sourceComponentsPage: SourceComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Sources', () => {
        navBarPage.goToEntity('source-grv');
        sourceComponentsPage = new SourceComponentsPage();
        expect(sourceComponentsPage.getTitle())
            .toMatch(/Sources/);

    });

    it('should load create Source dialog', () => {
        sourceComponentsPage.clickOnCreateButton();
        sourceDialogPage = new SourceDialogPage();
        expect(sourceDialogPage.getModalTitle())
            .toMatch(/Create or edit a Source/);
        sourceDialogPage.close();
    });

   /* it('should create and save Sources', () => {
        sourceComponentsPage.clickOnCreateButton();
        sourceDialogPage.setTitleInput('title');
        expect(sourceDialogPage.getTitleInput()).toMatch('title');
        sourceDialogPage.setUrlInput('url');
        expect(sourceDialogPage.getUrlInput()).toMatch('url');
        sourceDialogPage.statusSelectLastOption();
        sourceDialogPage.setLastRunDateInput(12310020012301);
        expect(sourceDialogPage.getLastRunDateInput()).toMatch('2001-12-31T02:30');
        sourceDialogPage.setInfoInput('info');
        expect(sourceDialogPage.getInfoInput()).toMatch('info');
        sourceDialogPage.areaSelectLastOption();
        sourceDialogPage.patternSelectLastOption();
        sourceDialogPage.save();
        expect(sourceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SourceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-source-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SourceDialogPage {
    modalTitle = element(by.css('h4#mySourceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    urlInput = element(by.css('input#field_url'));
    statusSelect = element(by.css('select#field_status'));
    lastRunDateInput = element(by.css('input#field_lastRunDate'));
    infoInput = element(by.css('input#field_info'));
    areaSelect = element(by.css('select#field_area'));
    patternSelect = element(by.css('select#field_pattern'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setUrlInput = function(url) {
        this.urlInput.sendKeys(url);
    };

    getUrlInput = function() {
        return this.urlInput.getAttribute('value');
    };

    setStatusSelect = function(status) {
        this.statusSelect.sendKeys(status);
    };

    getStatusSelect = function() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    };

    statusSelectLastOption = function() {
        this.statusSelect.all(by.tagName('option')).last().click();
    };
    setLastRunDateInput = function(lastRunDate) {
        this.lastRunDateInput.sendKeys(lastRunDate);
    };

    getLastRunDateInput = function() {
        return this.lastRunDateInput.getAttribute('value');
    };

    setInfoInput = function(info) {
        this.infoInput.sendKeys(info);
    };

    getInfoInput = function() {
        return this.infoInput.getAttribute('value');
    };

    areaSelectLastOption = function() {
        this.areaSelect.all(by.tagName('option')).last().click();
    };

    areaSelectOption = function(option) {
        this.areaSelect.sendKeys(option);
    };

    getAreaSelect = function() {
        return this.areaSelect;
    };

    getAreaSelectedOption = function() {
        return this.areaSelect.element(by.css('option:checked')).getText();
    };

    patternSelectLastOption = function() {
        this.patternSelect.all(by.tagName('option')).last().click();
    };

    patternSelectOption = function(option) {
        this.patternSelect.sendKeys(option);
    };

    getPatternSelect = function() {
        return this.patternSelect;
    };

    getPatternSelectedOption = function() {
        return this.patternSelect.element(by.css('option:checked')).getText();
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
