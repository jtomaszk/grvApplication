import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Pattern e2e test', () => {

    let navBarPage: NavBarPage;
    let patternDialogPage: PatternDialogPage;
    let patternComponentsPage: PatternComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Patterns', () => {
        navBarPage.goToEntity('pattern-grv');
        patternComponentsPage = new PatternComponentsPage();
        expect(patternComponentsPage.getTitle())
            .toMatch(/Patterns/);

    });

    it('should load create Pattern dialog', () => {
        patternComponentsPage.clickOnCreateButton();
        patternDialogPage = new PatternDialogPage();
        expect(patternDialogPage.getModalTitle())
            .toMatch(/Create or edit a Pattern/);
        patternDialogPage.close();
    });

    it('should create and save Patterns', () => {
        patternComponentsPage.clickOnCreateButton();
        patternDialogPage.setColumnsInput('columns');
        expect(patternDialogPage.getColumnsInput()).toMatch('columns');
        patternDialogPage.setValuesInput('values');
        expect(patternDialogPage.getValuesInput()).toMatch('values');
        patternDialogPage.save();
        expect(patternDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PatternComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-pattern-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PatternDialogPage {
    modalTitle = element(by.css('h4#myPatternLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    columnsInput = element(by.css('input#field_columns'));
    valuesInput = element(by.css('input#field_values'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setColumnsInput = function(columns) {
        this.columnsInput.sendKeys(columns);
    };

    getColumnsInput = function() {
        return this.columnsInput.getAttribute('value');
    };

    setValuesInput = function(values) {
        this.valuesInput.sendKeys(values);
    };

    getValuesInput = function() {
        return this.valuesInput.getAttribute('value');
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
