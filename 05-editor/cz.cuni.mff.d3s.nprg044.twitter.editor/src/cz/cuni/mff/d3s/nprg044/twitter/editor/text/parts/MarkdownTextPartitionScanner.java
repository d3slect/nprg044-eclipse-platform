package cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class MarkdownTextPartitionScanner extends RuleBasedPartitionScanner {
	public final static String MARKDOWN_H1 = "__markdown_h1";
	public final static String MARKDOWN_H2 = "__markdown_h2";
	public final static String MARKDOWN_BOLD = "__markdown_bold";
	public final static String MARKDOWN_ITALICS = "__markdown_italics";
	
	public final static String[] LEGAL_CONTENT_TYPES = {MARKDOWN_H1, MARKDOWN_H2, MARKDOWN_BOLD, MARKDOWN_ITALICS};
	
	public final static int NUMBER_OF_RULES = 4;
	
	public MarkdownTextPartitionScanner() {
		IToken heading1 = new Token(MARKDOWN_H1);
		IToken heading2 = new Token(MARKDOWN_H2);
		IToken bold = new Token(MARKDOWN_BOLD);
		IToken italics = new Token(MARKDOWN_ITALICS);
		
		// ORDER is important - try to switch order of '=' and '==' !
		IPredicateRule[] rules = new IPredicateRule[NUMBER_OF_RULES];
		rules[0] = new PatternRule("==", "==", heading2, (char) 0, false);
		rules[1] = new PatternRule("=", "=", heading1, (char) 0, false);		
		rules[2] = new MultiLineRule("*", "*", bold);			
		rules[3] = new MultiLineRule("_", "_", italics);
		
		setPredicateRules(rules);
	}	
}
