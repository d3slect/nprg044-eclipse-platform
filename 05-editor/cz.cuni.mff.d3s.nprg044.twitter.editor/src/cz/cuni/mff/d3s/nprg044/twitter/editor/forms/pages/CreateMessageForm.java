package cz.cuni.mff.d3s.nprg044.twitter.editor.forms.pages;

import java.util.Formatter;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class CreateMessageForm extends FormPage {
	
	public static final String[] IMAGE_EXTENSIONS = { "*.png", "*.jpg", "*.gif" };
	public static final String CHARACTER_COUNT_FORMAT = "<form><p><span color=\"NUMCOLOR\"><b>%d</b></span> characters</p></form>";
	public static final int MAX_CHARACTER_COUNT = 150;
	
	private TextViewer messageEditor;
	private FormText numOfCharactersText;
	private Text imageLocationText; 
	

	public CreateMessageForm(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		final FormToolkit toolkit = managedForm.getToolkit();

		form.setText("New message");

		TableWrapLayout layout = new TableWrapLayout();
		TableWrapData twd = null;
		layout.numColumns = 2;
		form.getBody().setLayout(layout);
		
		createMessageSection(form, toolkit);
		createImageSection(form, toolkit);
		
		Button sendButton = toolkit.createButton(form.getBody(), "Send", SWT.NULL);
		twd = new TableWrapData(TableWrapData.RIGHT, TableWrapData.BOTTOM);
		twd.colspan = 2;		
		twd.grabVertical = true;
		sendButton.setLayoutData(twd);
	}
	
	protected void createMessageSection(final ScrolledForm form, final FormToolkit toolkit) {
		TableWrapData twd = null;
		
		// create section with message text
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR | Section.EXPANDED);
				
		Composite sectionClient = toolkit.createComposite(section);		
		sectionClient.setLayout(new GridLayout(1, false));
		messageEditor = new TextViewer(sectionClient, SWT.BORDER | SWT.MULTI);
		toolkit.adapt(messageEditor.getControl(), true, true);
		messageEditor.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		messageEditor.getTextWidget().addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
//				updateCharactersCount(messageEditor.getTextWidget());				
			}
		});		
		
		numOfCharactersText = toolkit.createFormText(sectionClient, false);
		updateCharactersCount(MAX_CHARACTER_COUNT);
		numOfCharactersText.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
//		messageSourceViewer.getControl().setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);		
//		toolkit.paintBordersFor(sectionClient);
				
		section.setText("Message");
		section.setDescription("Write a message. But, its length should not exceed 150 characters.");
		section.setClient(sectionClient);
		twd = new TableWrapData(TableWrapData.FILL_GRAB);
		twd.colspan = 1;
		twd.grabHorizontal = true;
		twd.grabVertical = true;
		twd.heightHint = 200;
		section.setLayoutData(twd);
	}

	private Formatter formatter = new Formatter();
	
	protected void updateCharactersCount(int count) {
		String countString = formatter.format(CHARACTER_COUNT_FORMAT, count).toString();
		
		this.numOfCharactersText.setText(countString, true, false);
				
	}

	protected void createImageSection(final ScrolledForm form, final FormToolkit toolkit) {
		TableWrapData twd = null;
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR | Section.EXPANDED);
				
		Composite sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout(1, false));
		imageLocationText = toolkit.createText(sectionClient, "", SWT.BORDER);
		
		Button selectImageButton = toolkit.createButton(sectionClient, "Select...", SWT.NONE);
		selectImageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(e.display.getActiveShell(), SWT.OPEN);
				dialog.setFilterExtensions(IMAGE_EXTENSIONS);
				
				String selectedFile = dialog.open();
				if (selectedFile != null) {
					imageLocationText.setText(selectedFile);
				}
			}
		});
								
		section.setText("Attach image");
		section.setDescription("Use twitter-image service to attach the image.");
		section.setClient(sectionClient);
		twd = new TableWrapData(TableWrapData.FILL_GRAB);
		twd.colspan = 1;
		twd.grabHorizontal = true;
		twd.grabVertical = true;
		twd.heightHint = 200;
		section.setLayoutData(twd);		
	}
}
