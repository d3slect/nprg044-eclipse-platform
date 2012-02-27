package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class MarkdownContentAssistProcessor implements IContentAssistProcessor {
	
	private final static String[] PROPOSALS = { "=", "==", "*", "_" };

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
		for (int i = 0; i < PROPOSALS.length; i++) {
			result.add(new CompletionProposal(PROPOSALS[i], offset, 0, PROPOSALS[i].length()));			
		}
		
		return result.toArray(new ICompletionProposal[result.size()]);
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '=' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
}
