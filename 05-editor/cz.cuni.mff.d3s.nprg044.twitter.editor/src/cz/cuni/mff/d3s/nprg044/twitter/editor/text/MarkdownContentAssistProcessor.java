package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

/**
 * Responsible for text completion (hints) and context information.
 */
public class MarkdownContentAssistProcessor implements IContentAssistProcessor {
    private static final String[] PROPOSALS = { "=", "==", "*", "_" };

    /**
     * Return completion hints for the given offset. Here we always return all
     * supported markup symbols.
     */
    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
        List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

        for (int i = 0; i < PROPOSALS.length; i++) {
            result.add(new CompletionProposal(PROPOSALS[i], offset, 0, PROPOSALS[i].length()));
        }

        return result.toArray(new ICompletionProposal[result.size()]);
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
        return null;
    }

    // completion hints triggered when the user types '='
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
