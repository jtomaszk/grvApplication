import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Area e2e test', () => {

    let navBarPage: NavBarPage;
    let areaDialogPage: AreaDialogPage;
    let areaComponentsPage: AreaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Areas', () => {
        navBarPage.goToEntity('area-grv');
        areaComponentsPage = new AreaComponentsPage();
        expect(areaComponentsPage.getTitle())
            .toMatch(/Areas/);

    });

    it('should load create Area dialog', () => {
        areaComponentsPage.clickOnCreateButton();
        areaDialogPage = new AreaDialogPage();
        expect(areaDialogPage.getModalTitle())
            .toMatch(/Create or edit a Area/);
        areaDialogPage.close();
    });

    it('should create and save Areas', () => {
        areaComponentsPage.clickOnCreateButton();
        areaDialogPage.setAreaNameInput('areaName');
        expect(areaDialogPage.getAreaNameInput()).toMatch('areaName');
        areaDialogPage.save();
        expect(areaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AreaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-area-grv div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class AreaDialogPage {
    modalTitle = element(by.css('h4#myAreaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    areaNameInput = element(by.css('input#field_areaName'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setAreaNameInput = function(areaName) {
        this.areaNameInput.sendKeys(areaName);
    };

    getAreaNameInput = function() {
        return this.areaNameInput.getAttribute('value');
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
