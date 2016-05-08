package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors.ColorManager;
import cz.cuni.mff.d3s.nprg044.twitter.editor.text.outline.MarkdownTextOutlinePage;

/**
 * Text editor implements the IContentOutlinePage object through an adapter.
 */
public class MarkdownTextEditor extends TextEditor {

    public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.editor.MarkdownTextEditor";

    private ColorManager colorManager;

    // page that will show the text structure (headings)
    private MarkdownTextOutlinePage outlinePage;

    @Override
    protected void initializeEditor() {
        super.initializeEditor();

        colorManager = new ColorManager();

        // setup configuration of some graphical presentation aspects of the
        // editor's raw content
        setSourceViewerConfiguration(new MarkdownTextEditorConfiguration(colorManager));

        // setup document provider
        setDocumentProvider(new MarkdownTextDocumentProvider());
    }

    @Override
    public void dispose() {
        colorManager.dispose();

        super.dispose();
    }

    /**
     * Returns an object of the IContentOutlinePage class that is associated
     * with this editor. The page is displayed inside the outline window.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object getAdapter(Class adapter) {
        if (IContentOutlinePage.class.equals(adapter)) {
            if (outlinePage == null) {
                outlinePage = new MarkdownTextOutlinePage();
                outlinePage.setInput(getDocumentProvider().getDocument(getEditorInput()));

                // outline page will be notified about changes of the caret
                // position
                IPostSelectionProvider provider = (IPostSelectionProvider) getSelectionProvider();
                provider.addPostSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ITextSelection textSelection = (ITextSelection) event.getSelection();
                        outlinePage.select(textSelection);
                    }

                });

                // register a listener for selection of elements in the content
                // outline page
                outlinePage.addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                        if (!selection.isEmpty()) {
                            // show and highlight the selected text region
                            ITypedRegion region = (ITypedRegion) selection.getFirstElement();
                            selectAndReveal(region.getOffset(), region.getLength());
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
