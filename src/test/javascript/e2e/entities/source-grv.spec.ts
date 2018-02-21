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

    it('should create and save Sources', () => {
        sourceComponentsPage.clickOnCreateButton();
        sourceDialogPage.setTitleInput('title');
        expect(sourceDialogPage.getTitleInput()).toMatch('title');
        sourceDialogPage.setUrlInput('url');
        expect(sourceDialogPage.getUrlInput()).toMatch('url');
        sourceDialogPage.setJsonInput('json');
        expect(sourceDialogPage.getJsonInput()).toMatch('json');
        sourceDialogPage.areaSelectLastOption();
        sourceDialogPage.save();
        expect(sourceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

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
    jsonInput = element(by.css('textarea#field_json'));
    areaSelect = element(by.css('select#field_area'));

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

    setJsonInput = function(json) {
        this.jsonInput.sendKeys(json);
    };

    getJsonInput = function() {
        return this.jsonInput.getAttribute('value');
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
