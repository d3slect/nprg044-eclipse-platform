package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts.MarkdownTextPartitionScanner;

public class MarkdownTextDocumentProvider extends FileDocumentProvider {
	
	@Override
	protected IDocument createDocument(Object element) throws CoreException {		
		IDocument document = super.createDocument(element);
		
		// register document partitioner
		if (document != null) {
			IDocumentPartitioner partitioner = 
				new FastPartitioner(
						new MarkdownTextPartitionScanner(), 
						MarkdownTextPartitionScanner.LEGAL_CONTENT_TYPES);
			
			// connect it with document
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
				
		return document;
	}

}
