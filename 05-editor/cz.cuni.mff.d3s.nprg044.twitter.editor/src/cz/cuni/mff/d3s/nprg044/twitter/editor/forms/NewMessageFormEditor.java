package cz.cuni.mff.d3s.nprg044.twitter.editor.forms;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import cz.cuni.mff.d3s.nprg044.twitter.editor.forms.pages.CreateMessageForm;

public class NewMessageFormEditor extends FormEditor {
	private static final int NEW_MESSAGE_PAGE_INDEX = 0;
	private static final int MESSAGE_SOURCE_VIEWER_PAGE_INDEX = 1;
	
	private CreateMessageForm newMessageForm;
	private TextViewer messageSourceTextViewer;

	@Override
	protected void addPages() {
		addPage0();
		addPage1();
	}
	
	private void addPage0() {
		try {
			newMessageForm = new CreateMessageForm(this, "cz.cuni.mff.d3s.nprg044.twitter.editor.forms", "Message");
			addPage(NEW_MESSAGE_PAGE_INDEX, newMessageForm);
		} catch (PartInitException e) {
			e.printStackTrace();
		}		
	}
	
	private void addPage1() {
		Composite composite = new Composite(getContainer(), SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		composite.setLayout(fillLayout);
		
		messageSourceTextViewer = new TextViewer(composite, SWT.BORDER | SWT.FLAT | SWT.READ_ONLY);
		
		addPage(MESSAGE_SOURCE_VIEWER_PAGE_INDEX, composite);
		setPageText(MESSAGE_SOURCE_VIEWER_PAGE_INDEX, "Source");		
	}
	

	@Override
	public void doSave(IProgressMonitor monitor) {		
	}

	@Override
	public void doSaveAs() {		
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
