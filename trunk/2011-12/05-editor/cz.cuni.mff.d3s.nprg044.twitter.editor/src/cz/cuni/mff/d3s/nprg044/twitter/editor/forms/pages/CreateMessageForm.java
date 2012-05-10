package cz.cuni.mff.d3s.nprg044.twitter.editor.forms.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Formatter;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import cz.cuni.mff.d3s.nprg044.twitter.editor.model.TwitterMessage;

public class CreateMessageForm extends FormPage {
	
	public static final String PAGE_ID = "cz.cuni.mff.d3s.nprg044.twitter.editor.forms.messageEdit";
	
	public static final String[] IMAGE_EXTENSIONS = { "*.png", "*.jpg", "*.gif" };
	public static final String CHARACTER_COUNT_FORMAT = "<form><p>Remain <b>%d</b> characters</p></form>";
	public static final int MAX_CHARACTER_COUNT = 150;
	
	private TextViewer messageEditor;
	private FormText numOfCharactersText;
	private Text imageLocationText; 
	
	private Image imageToUpload;
	private static Image ERROR_IMAGE = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
	private static Image DEFAULT_IMAGE = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	private Image imageToDraw = DEFAULT_IMAGE;

	private TwitterMessage initialMessage;
	
	public CreateMessageForm(FormEditor editor) {
		super(editor, PAGE_ID, "Message");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		final FormToolkit toolkit = managedForm.getToolkit();		
				
		form.setText("New message");
		toolkit.decorateFormHeading(form.getForm());
		
		TableWrapLayout layout = new TableWrapLayout();
		TableWrapData twd = null;
		layout.numColumns = 2;
		form.getBody().setLayout(layout);
		
		createMessageSection(form, toolkit);
		createImageSection(form, toolkit);
		
		Button sendButton = toolkit.createButton(form.getBody(), "Send", SWT.NULL);
		twd = new TableWrapData(TableWrapData.RIGHT, TableWrapData.BOTTOM);				
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
				
				e.display.asyncExec(new Runnable() {
					
					@Override
					public void run() {
						updateCharactersCount(MAX_CHARACTER_COUNT - messageEditor.getTextWidget().getText().length());						
					}
				});								
			}
		});		
		if (initialMessage!=null && initialMessage.hasText()) {
			messageEditor.getTextWidget().setText(initialMessage.getText());
		}
		
		numOfCharactersText = toolkit.createFormText(sectionClient, false);
		updateCharactersCount(MAX_CHARACTER_COUNT);
		numOfCharactersText.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));	
						
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

		
	protected void updateCharactersCount(int count) {
		String countString = String.format(CHARACTER_COUNT_FORMAT, count);
		
		this.numOfCharactersText.setText(countString, true, false);
				
	}

	protected void createImageSection(final ScrolledForm form, final FormToolkit toolkit) {
		TableWrapData twd = null;
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR | Section.EXPANDED);
				
		final Composite sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout(2, false));
		imageLocationText = toolkit.createText(sectionClient, "", SWT.BORDER);
		imageLocationText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		if (initialMessage!=null && initialMessage.hasImage()) { 
			imageLocationText.setText(initialMessage.getImagePath());
			loadImage(initialMessage.getImagePath());
		}
		
		Button selectImageButton = toolkit.createButton(sectionClient, "Select...", SWT.NONE);
		selectImageButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
		
		final Canvas canvas = new Canvas(sectionClient, SWT.NONE);		
		toolkit.adapt(canvas, false, false);
		canvas.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		canvas.addPaintListener(new PaintListener() {			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(imageToDraw, 0, 0);				
			}
		});
		
		selectImageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(e.display.getActiveShell(), SWT.OPEN);
				dialog.setFilterExtensions(IMAGE_EXTENSIONS);
				
				final String selectedFile = dialog.open();
				if (selectedFile != null) {
					imageLocationText.setText(selectedFile);
					e.display.asyncExec(new Runnable() {
						
						@Override
						public void run() {
							loadImage(selectedFile);
							canvas.redraw();
							sectionClient.layout(true);
						}
					});
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
	
	private void loadImage(String imageFile) {
		if (imageToUpload != null) {
			imageToUpload.dispose();
			imageToUpload = null;
		}
		
		try {
			URL url = new URL("file://" + imageFile);
			ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(url);
			imageToUpload = imgDescriptor.createImage(); 
					
			imageToDraw = imageToUpload;
		} catch (MalformedURLException e) {
			imageToDraw = ERROR_IMAGE;			
		} 
		
	}
	
	public void fill(final TwitterMessage message) {
		messageEditor.getTextWidget().setText(message.getText());
		if (message.hasImage()) {
			imageLocationText.setText(message.getImagePath());
			loadImage(message.getImagePath());
		}
	}
	
	public TwitterMessage getTwitterMessage() {
		TwitterMessage twitterMessage = new TwitterMessage();
		
		twitterMessage.setText(messageEditor.getTextWidget().getText());
		twitterMessage.setImagePath(imageLocationText.getText());
		
		return twitterMessage;
	}
	
	@Override
	public void dispose() {
		if (imageToUpload != null) {
			imageToUpload.dispose();
		}
		
		super.dispose();
	}

	public void setInitialMessage(TwitterMessage msg) {
		this.initialMessage = msg;		
	}
}
