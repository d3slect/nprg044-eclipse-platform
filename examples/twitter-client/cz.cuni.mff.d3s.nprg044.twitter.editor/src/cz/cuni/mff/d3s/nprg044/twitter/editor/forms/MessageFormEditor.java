package cz.cuni.mff.d3s.nprg044.twitter.editor.forms;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;

import cz.cuni.mff.d3s.nprg044.twitter.editor.TwitterEditorPlugin;
import cz.cuni.mff.d3s.nprg044.twitter.editor.forms.pages.CreateMessageForm;
import cz.cuni.mff.d3s.nprg044.twitter.editor.model.TwitterMessage;

/**
 * Form editor with two pages: one shows the raw source code, and the second allows more "user-friendly"
 * editing of the message through a nice form.
 */
public class MessageFormEditor extends FormEditor {
	private static final int MESSAGE_EDIT_PAGE_INDEX = 0;
	private static final int MESSAGE_SOURCE_VIEWER_PAGE_INDEX = 1;
	
	private CreateMessageForm messageEditPage;	
	private TextEditor textEditor;
	
	@Override
	protected void addPages() {		
		// message-editing form
		addPage0();
		// raw source code
		addPage1();
		
		// fill page 0
		setInitialMessage(); 
	}
	
	private void addPage0() {
		try {
			messageEditPage = new CreateMessageForm(this);			
			addPage(MESSAGE_EDIT_PAGE_INDEX, messageEditPage);			
		} 
		catch (PartInitException e) {
			TwitterEditorPlugin.logException(e);
		}		
	}
	
	private void addPage1() {				
		textEditor = new TextEditor();	
		
		try {
			addPage(MESSAGE_SOURCE_VIEWER_PAGE_INDEX, textEditor, getEditorInput());
			setPageText(MESSAGE_SOURCE_VIEWER_PAGE_INDEX, "Source");
		} 
		catch (PartInitException e) {
			TwitterEditorPlugin.logException(e);
		}				
	}
	
	protected String[] getDocument() throws BadLocationException {
		IDocument document = textEditor.getDocumentProvider().getDocument(getEditorInput());
		int numOfLines = document.getNumberOfLines();
		String[] lines = new String[numOfLines];
		for (int i = 0; i < numOfLines; i++) {
			lines[i] = document.get(document.getLineOffset(i), document.getLineLength(i));
		}
								
		return lines;  				
	}
		
	@Override
	public void doSave(IProgressMonitor monitor) {
		commitPages(true);		
		textEditor.doSave(monitor);
		editorDirtyStateChanged();
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		int oldIndex = getCurrentPage();
		
		if (oldIndex == MESSAGE_EDIT_PAGE_INDEX) {
			fillTextEditor();			
		} 
		else if (oldIndex == MESSAGE_SOURCE_VIEWER_PAGE_INDEX) {
			fillMessageEditPage();
		}		
		
		super.pageChange(newPageIndex);
	}
	
	protected void fillTextEditor() {
		TwitterMessage msg = messageEditPage.getTwitterMessage();
		IDocument document = textEditor.getDocumentProvider().getDocument(getEditorInput());
		document.set(msg.toString());		
	}
	
	protected void fillMessageEditPage() {
		try {
			TwitterMessage msg = new TwitterMessage(getDocument());
			messageEditPage.fill(msg);
		} 
		catch (BadLocationException e) {
			TwitterEditorPlugin.logException(e);
		}
	}
	
	protected void setInitialMessage() {
		try {
			TwitterMessage msg = new TwitterMessage(getDocument());
			messageEditPage.setInitialMessage(msg);
		} 
		catch (BadLocationException e) {
			TwitterEditorPlugin.logException(e);
		}
	}
}
