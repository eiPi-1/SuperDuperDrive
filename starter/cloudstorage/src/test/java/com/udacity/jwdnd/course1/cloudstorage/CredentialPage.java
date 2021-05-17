package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialsTabField;

    @FindBy(css = "#new-credential-btn")
    private WebElement newCredentialBtnField;

    @FindBy(css = "#credential-url")
    private WebElement credentialTitleField;

    @FindBy(css = "#credential-username")
    private WebElement credentialUsernameField;

    @FindBy(css = "#credential-password")
    private WebElement credentialPasswordField;

    @FindBy(css = "#credentialSubmit")
    private WebElement credentialSaveBtnField;

    @FindBy(css = "#credential-success-message")
    private WebElement credentialSuccessMessageField;

    @FindBy(css = "#edit-credential")
    private WebElement editCredentialBtnField;

    @FindBy(css = "#delete-credential")
    private WebElement deleteCredentialBtnField;

    private final JavascriptExecutor javascriptExecutor;

    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
    }

    public void clickCredentialsTab(){
        this.javascriptExecutor.executeScript("arguments[0].click();", credentialsTabField);
    }

    public void clickNewCredentialBtn(){
        this.javascriptExecutor.executeScript("arguments[0].click();", newCredentialBtnField);
    }

    public void fillCredential(String url, String username, String password){
        javascriptExecutor.executeScript("arguments[0].value='" + url + "';", credentialTitleField);
        javascriptExecutor.executeScript("arguments[0].value='" + username + "';", credentialUsernameField);
        javascriptExecutor.executeScript("arguments[0].value='" + password + "';", credentialPasswordField);
    }

    public void submitCredential(){
        this.javascriptExecutor.executeScript("arguments[0].click();", credentialSaveBtnField);
    }

    public void createNewCredential(String url, String username, String password){
        this.clickCredentialsTab();
        this.clickNewCredentialBtn();
        this.fillCredential(url, username, password);
        this.submitCredential();
    }

    public String getSuccessMessage(){
        return credentialSuccessMessageField.getAttribute("innerHTML");
    }

    public void editCredential(String url, String username, String password){
        this.clickCredentialsTab();
        this.javascriptExecutor.executeScript("arguments[0].click();", editCredentialBtnField);
        this.fillCredential(url, username, password);
        this.submitCredential();
    }

    public void deleteCredential(){
        this.clickCredentialsTab();
        this.javascriptExecutor.executeScript("arguments[0].click();", deleteCredentialBtnField);
    }
}
