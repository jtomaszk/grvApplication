import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('InputPattern e2e test', () => {

    let navBarPage: NavBarPage;
    let inputPatternDialogPage: InputPatternDialogPage;
    let inputPatternComponentsPage: InputPatternComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load InputPatterns', () => {
        navBarPage.goToEntity('input-pattern-grv');
        inputPatternComponentsPage = new InputPatternComponentsPage();
        expect(inputPatternComponentsPage.getTitle())
            .toMatch(/Input Patterns/);

    });

    it('should load create InputPattern dialog', () => {
        inputPatternComponentsPage.clickOnCreateButton();
        inputPatternDialogPage = new InputPatternDialogPage();
        expect(inputPatternDialogPage.getModalTitle())
            .toMatch(/Create or edit a Input Pattern/);
        inputPatternDialogPage.close();
    });

    it('should create and save InputPatterns', () => {
        inputPatternComponentsPage.clickOnCreateButton();
        inputPatternDialogPage.setTitleInput('title');
        expect(inputPatternDialogPage.getTitleInput()).toMatch('title');
        inputPatternDialogPage.save();
        expect(inputPatternDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InputPatternComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-input-pattern-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class InputPatternDialogPage {
    modalTitle = element(by.css('h4#myInputPatternLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
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
