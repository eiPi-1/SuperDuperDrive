package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {

    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTabField;

    @FindBy(css = "#new-note-btn")
    private WebElement newNoteBtnField;

    @FindBy(css = "#note-title")
    private WebElement noteTitleField;

    @FindBy(css = "#note-description")
    private WebElement noteDescriptionField;

    @FindBy(css = "#noteSubmit")
    private WebElement noteSaveBtnField;

    @FindBy(css = "#note-success-message")
    private WebElement noteSuccessMessageField;

    @FindBy(css = "#edit-note")
    private WebElement editNoteBtnField;

    @FindBy(css = "#delete-note")
    private WebElement deleteNoteBtnField;

    private final JavascriptExecutor javascriptExecutor;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
    }

    public void clickNoteTab(){
        this.javascriptExecutor.executeScript("arguments[0].click();", notesTabField);
    }

    public void clickNewNoteBtn(){
        this.javascriptExecutor.executeScript("arguments[0].click();", newNoteBtnField);
    }

    public void fillNote(String title, String description){
        javascriptExecutor.executeScript("arguments[0].value='" + title + "';", noteTitleField);
        javascriptExecutor.executeScript("arguments[0].value='" + description + "';", noteDescriptionField);
    }

    public void submitNote(){
        this.javascriptExecutor.executeScript("arguments[0].click();", noteSaveBtnField);
    }

    public void createNewNote(String title, String description){
        this.clickNoteTab();
        this.clickNewNoteBtn();
        this.fillNote(title, description);
        this.submitNote();
    }

    public String getSuccessMessage(){
        return noteSuccessMessageField.getAttribute("innerHTML");
    }

    public void editNote(String title, String description){
        this.clickNoteTab();
        this.javascriptExecutor.executeScript("arguments[0].click();", editNoteBtnField);
        this.fillNote(title, description);
        this.submitNote();
    }

    public void deleteNote(){
        this.clickNoteTab();
        this.javascriptExecutor.executeScript("arguments[0].click();", deleteNoteBtnField);
    }
}
