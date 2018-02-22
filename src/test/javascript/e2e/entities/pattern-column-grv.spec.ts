import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PatternColumn e2e test', () => {

    let navBarPage: NavBarPage;
    let patternColumnDialogPage: PatternColumnDialogPage;
    let patternColumnComponentsPage: PatternColumnComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PatternColumns', () => {
        navBarPage.goToEntity('pattern-column-grv');
        patternColumnComponentsPage = new PatternColumnComponentsPage();
        expect(patternColumnComponentsPage.getTitle())
            .toMatch(/Pattern Columns/);

    });

    it('should load create PatternColumn dialog', () => {
        patternColumnComponentsPage.clickOnCreateButton();
        patternColumnDialogPage = new PatternColumnDialogPage();
        expect(patternColumnDialogPage.getModalTitle())
            .toMatch(/Create or edit a Pattern Column/);
        patternColumnDialogPage.close();
    });

   /* it('should create and save PatternColumns', () => {
        patternColumnComponentsPage.clickOnCreateButton();
        patternColumnDialogPage.columnSelectLastOption();
        patternColumnDialogPage.setValueInput('value');
        expect(patternColumnDialogPage.getValueInput()).toMatch('value');
        patternColumnDialogPage.patternSelectLastOption();
        patternColumnDialogPage.save();
        expect(patternColumnDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PatternColumnComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-pattern-column-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PatternColumnDialogPage {
    modalTitle = element(by.css('h4#myPatternColumnLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    columnSelect = element(by.css('select#field_column'));
    valueInput = element(by.css('input#field_value'));
    patternSelect = element(by.css('select#field_pattern'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setColumnSelect = function(column) {
        this.columnSelect.sendKeys(column);
    };

    getColumnSelect = function() {
        return this.columnSelect.element(by.css('option:checked')).getText();
    };

    columnSelectLastOption = function() {
        this.columnSelect.all(by.tagName('option')).last().click();
    };
    setValueInput = function(value) {
        this.valueInput.sendKeys(value);
    };

    getValueInput = function() {
        return this.valueInput.getAttribute('value');
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
