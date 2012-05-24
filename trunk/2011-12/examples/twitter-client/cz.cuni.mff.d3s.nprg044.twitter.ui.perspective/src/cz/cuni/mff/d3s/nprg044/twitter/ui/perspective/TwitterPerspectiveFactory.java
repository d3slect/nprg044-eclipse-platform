package cz.cuni.mff.d3s.nprg044.twitter.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.TwitterMessageTimelineView;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.UserViewPart;
import cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.LoginWizard;
import cz.cuni.mff.d3s.nprg044.twitter.wizards.NewMessageWizard;

public class TwitterPerspectiveFactory implements IPerspectiveFactory {

	/** The ID of this GUI element. */
    public static final String PERSPECTIVE_ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.perspective.TwitterPerspective";

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    @Override
    public void createInitialLayout(IPageLayout layout) {
            addViews(layout);
            addActionSets(layout);
            addNewWizardShortcuts(layout);
            addPerspectiveShortcuts(layout);
            addViewShortcuts(layout);
    }

    /**
     * Adds the views.
     */
    private void addViews(IPageLayout layout) {
            // create left panel view area
            IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.35f, layout.getEditorArea());
            
            left.addView(TwitterMessageTimelineView.ID);
            left.addView(IPageLayout.ID_PROP_SHEET);

            // create bottom panel view area
            IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, layout.getEditorArea());
            bottom.addView(IPageLayout.ID_PROP_SHEET);
            bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
            bottom.addView("org.eclipse.pde.runtime.LogView");

            // create right panel view area
            IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, 0.35f, layout.getEditorArea());
            right.addView(UserViewPart.ID);
    }

    /**
     * Adds the action sets.
     */
    private void addActionSets(IPageLayout layout) {
//            layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
    }

    /**
     * Adds the perspective shortcuts.
     */
    private void addPerspectiveShortcuts(IPageLayout layout) {
            layout.addPerspectiveShortcut(PERSPECTIVE_ID);
    }

    /**
     * Adds the new wizard shortcuts.
     */
    private void addNewWizardShortcuts(IPageLayout layout) { 
    		layout.addNewWizardShortcut(LoginWizard.ID);
            layout.addNewWizardShortcut(NewMessageWizard.ID);
    }

    /**
     * Adds the view shortcuts.
     */
    private void addViewShortcuts(IPageLayout layout) {
//    	layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
    }
}
