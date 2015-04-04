package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors.ColorManager;
import cz.cuni.mff.d3s.nprg044.twitter.editor.text.outline.MarkdownTextOutlinePage;

public class MarkdownTextEditor extends TextEditor {
	
	public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.editor.MarkdownTextEditor";
	
	private ColorManager colorManager;
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		
		colorManager = new ColorManager();
		
		// setup TextEditor 
		setSourceViewerConfiguration(new MarkdownTextEditorConfiguration(colorManager));

		// setup document provider - or use IDocumentSetupParticipant and register an extension  
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
//				((ISelectionService) getSite().getService(ISelectionService.class)).addSelectionListener(outlinePage);
				
				// register listener on change of the input				
				getSourceViewer().getTextWidget().addCaretListener(outlinePage);
				outlinePage.addSelectionChangedListener(new ISelectionChangedListener() {
					
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) event.getSelection();
						if (!selection.isEmpty()) {
							ITypedRegion region = (ITypedRegion) selection.getFirstElement();
//							selectAndReveal(region.getOffset(), region.getLength());
							setHighlightRange(region.getOffset(), region.getLength(), true);
						}						
					}
				});
			}	
			
			return outlinePage;
		} 
		
		return super.getAdapter(adapter);
	}

}
