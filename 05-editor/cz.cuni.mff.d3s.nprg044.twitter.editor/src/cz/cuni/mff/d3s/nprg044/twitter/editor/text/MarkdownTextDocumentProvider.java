package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts.MarkdownTextPartitionScanner;

/**
 * A DocumentProvider object represents a mapping between the editor input and 
 * its graphical presentation in the text editor widget. 
 */
public class MarkdownTextDocumentProvider extends FileDocumentProvider {
	
	/**
	 * Return an IDocument object that allows to work with the actual text representation
	 * of the document shown in the editor widget (control) - to access lines, chars, 
	 * update the current position, replace text fragments, and so on.
	 */
	@Override
	protected IDocument createDocument(Object element) throws CoreException {		
		IDocument document = super.createDocument(element);
		
		// register the document partitioner
		if (document != null) {
			IDocumentPartitioner partitioner = new FastPartitioner(
					new MarkdownTextPartitionScanner(), 
					MarkdownTextPartitionScanner.LEGAL_CONTENT_TYPES
				);
			
			// connect the partitioner with the document
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
				
		return document;
	}
}
