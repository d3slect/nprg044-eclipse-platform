package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors.ColorManager;
import cz.cuni.mff.d3s.nprg044.twitter.editor.text.outline.MarkdownTextOutlinePage;

public class MarkdownTextEditor extends TextEditor {
	
	private ColorManager colorManager;
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		
		colorManager = new ColorManager();
		
		// setup TextEditor
		setSourceViewerConfiguration(new MarkdownTextEditorConfiguration(colorManager));
		// setup document provider
		setDocumentProvider(new MarkdownTextDocumentProvider());
	}	
	
	@Override
	public void dispose() {
		colorManager.dispose();
		
		super.dispose();
	}
	
	private MarkdownTextOutlinePage outlinePage;
	
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage  == null) {
				outlinePage = new MarkdownTextOutlinePage();
				outlinePage.setInput(getDocumentProvider().getDocument(getEditorInput()));
			}	
			
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}

}
