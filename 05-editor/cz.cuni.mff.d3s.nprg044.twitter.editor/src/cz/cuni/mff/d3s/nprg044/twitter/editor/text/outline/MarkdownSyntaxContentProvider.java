package cz.cuni.mff.d3s.nprg044.twitter.editor.text.outline;

import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts.MarkdownTextPartitionScanner;

/**
 * This content provider represents a hierarchy of headings of the levels H1 and
 * H2.
 */
public class MarkdownSyntaxContentProvider implements ITreeContentProvider {
    private static final Object[] EMPTY = {};
    private TreeViewer viewer;
    private IDocument document;

    private IDocumentListener documentListener = new IDocumentListener() {
        @Override
        public void documentChanged(DocumentEvent event) {
            if (!MarkdownSyntaxContentProvider.this.viewer.getControl().isDisposed()) {
                MarkdownSyntaxContentProvider.this.viewer.refresh();
            }
        }

        @Override
        public void documentAboutToBeChanged(DocumentEvent event) {
        }
    };

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.viewer = (TreeViewer) viewer;

        if (oldInput instanceof IDocument) {
            document.removeDocumentListener(documentListener);
        }

        if (newInput instanceof IDocument) {
            document = (IDocument) newInput;
            document.addDocumentListener(documentListener);
        }
    }

    @Override
    public Object[] getElements(Object inputElement) {
        IDocument document = getDocument(inputElement);
        if (document != null) {
            ArrayList<ITypedRegion> result = new ArrayList<ITypedRegion>();
            try {
                // get all partitions in the document
                ITypedRegion[] regions = document.computePartitioning(0, document.getLength());
                // headings of the type H1 are the top level elements
                for (ITypedRegion region : regions) {
                    if (region.getType().equals(MarkdownTextPartitionScanner.MARKDOWN_H1)) {
                        result.add(region);
                    }
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            return result.toArray();
        }

        return EMPTY;
    }

    private IDocument getDocument(Object inputElement) {
        if (inputElement instanceof IDocument) {
            return (IDocument) inputElement;
        }

        return null;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ITypedRegion) {
            ITypedRegion parentRegion = (ITypedRegion) parentElement;

            ArrayList<ITypedRegion> result = new ArrayList<ITypedRegion>();

            if (parentRegion.getType().equals(MarkdownTextPartitionScanner.MARKDOWN_H1)) {
                ITypedRegion[] regions;
                try {
                    // get all partitions in the given region (heading H1)
                    regions = document.computePartitioning(parentRegion.getOffset() + parentRegion.getLength(),
                            document.getLength() - parentRegion.getOffset() - parentRegion.getLength());

                    // get all headings of the second level (H2) in the scope of
                    // the current first-level heading (H1)
                    for (ITypedRegion h2region : regions) {
                        if (h2region.getType().equals(MarkdownTextPartitionScanner.MARKDOWN_H2)) {
                            result.add(h2region);
                        }
                        if (h2region.getType().equals(MarkdownTextPartitionScanner.MARKDOWN_H1)) {
                            break;
                        }
                    }

                    return result.toArray();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        return EMPTY;
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof ITypedRegion) {
            ITypedRegion region = (ITypedRegion) element;
            if (region.getType().equals(MarkdownTextPartitionScanner.MARKDOWN_H1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
    }
}
