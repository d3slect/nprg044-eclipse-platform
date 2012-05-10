package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors.ColorManager;
import cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors.IMarkdownTextColorConstants;
import cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts.MarkdownTextPartitionScanner;

public class MarkdownTextEditorConfiguration extends SourceViewerConfiguration {

	private RuleBasedScanner markdownDefaultTextScanner;
	private RuleBasedScanner markdownH1Scanner;
	private RuleBasedScanner markdownH2Scanner;
	private RuleBasedScanner markdownBoldTextScanner;
	private RuleBasedScanner markdownItalicsTextScanner;
	
	private ColorManager colorManager;

	public MarkdownTextEditorConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return new MarkdownTextHover();
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		IContentAssistProcessor sharedProcessor = new MarkdownContentAssistProcessor();
		assistant.setContentAssistProcessor(sharedProcessor, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_H1);
		assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_H2);
		assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_BOLD);
		assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_ITALICS);		
		
		assistant.setEmptyMessage("Sorry, no hint for you :-/");
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(500);
		
		return assistant;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				MarkdownTextPartitionScanner.MARKDOWN_H1,
				MarkdownTextPartitionScanner.MARKDOWN_H2,
				MarkdownTextPartitionScanner.MARKDOWN_BOLD,
				MarkdownTextPartitionScanner.MARKDOWN_ITALICS };
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		DefaultDamagerRepairer dr = null;

		dr = new DefaultDamagerRepairer(
				getMarkdownH1Scanner());
		reconciler.setDamager(dr, MarkdownTextPartitionScanner.MARKDOWN_H1);
		reconciler.setRepairer(dr, MarkdownTextPartitionScanner.MARKDOWN_H1);
		
		dr = new DefaultDamagerRepairer(
				getMarkdownH2Scanner());
		reconciler.setDamager(dr, MarkdownTextPartitionScanner.MARKDOWN_H2);
		reconciler.setRepairer(dr, MarkdownTextPartitionScanner.MARKDOWN_H2);
		
		dr = new DefaultDamagerRepairer(
				getMarkdownBoldTextScanner());
		reconciler.setDamager(dr, MarkdownTextPartitionScanner.MARKDOWN_BOLD);
		reconciler.setRepairer(dr, MarkdownTextPartitionScanner.MARKDOWN_BOLD);
		
		dr = new DefaultDamagerRepairer(
				getMarkdownItalicsTextScanner());
		reconciler.setDamager(dr, MarkdownTextPartitionScanner.MARKDOWN_ITALICS);
		reconciler.setRepairer(dr, MarkdownTextPartitionScanner.MARKDOWN_ITALICS);

		dr = new DefaultDamagerRepairer(getMarkdownDefaultTextScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

	private ITokenScanner getMarkdownH1Scanner() {
		if (markdownH1Scanner == null) {
			markdownH1Scanner = new RuleBasedScanner() {
				{
					setDefaultReturnToken(new Token(new TextAttribute(
							colorManager.getColor(IMarkdownTextColorConstants.H1),
							colorManager.getColor(IMarkdownTextColorConstants.H1_BG),
							TextAttribute.UNDERLINE
							)));
				}
			};
		}
		return markdownH1Scanner;
	}

	private ITokenScanner getMarkdownH2Scanner() {
		if (markdownH2Scanner == null) {
			markdownH2Scanner = new RuleBasedScanner() {
				{
					setDefaultReturnToken(new Token(new TextAttribute(
							colorManager.getColor(IMarkdownTextColorConstants.H2),
							colorManager.getColor(IMarkdownTextColorConstants.H2_BG),
							SWT.NONE
							)));
				}
			};
		}
		return markdownH2Scanner;
	}

	private ITokenScanner getMarkdownBoldTextScanner() {
		if (markdownBoldTextScanner == null) {
			markdownBoldTextScanner = new RuleBasedScanner() {
				{
					setDefaultReturnToken(new Token(
							new TextAttribute(
									colorManager.getColor(IMarkdownTextColorConstants.BOLD_TEXT),
									null,
									SWT.BOLD)));
				}
			};
		}
		return markdownBoldTextScanner;
	}

	private ITokenScanner getMarkdownItalicsTextScanner() {
		if (markdownItalicsTextScanner == null) {
			markdownItalicsTextScanner = new RuleBasedScanner() {
				{
					setDefaultReturnToken(new Token(
							new TextAttribute(
									colorManager.getColor(IMarkdownTextColorConstants.ITALICS_TEXT),
									null,
									SWT.ITALIC)));
				}
			};
		}
		return markdownItalicsTextScanner;
	}

	private ITokenScanner getMarkdownDefaultTextScanner() {
		if (markdownDefaultTextScanner == null) {
			markdownDefaultTextScanner = new RuleBasedScanner() {
				{
					setDefaultReturnToken(new Token(
							new TextAttribute(
									colorManager.getColor(IMarkdownTextColorConstants.DEFAULT_TEXT))));
				}
			};

		}
		return markdownDefaultTextScanner;
	}
}
