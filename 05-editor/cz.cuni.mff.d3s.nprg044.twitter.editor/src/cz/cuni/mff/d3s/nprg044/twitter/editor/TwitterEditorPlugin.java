package cz.cuni.mff.d3s.nprg044.twitter.editor;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TwitterEditorPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "cz.cuni.mff.d3s.nprg044.twitter.editor"; //$NON-NLS-1$

    // The shared instance
    private static TwitterEditorPlugin plugin;

    public TwitterEditorPlugin() {
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     */
    public static TwitterEditorPlugin getDefault() {
        return plugin;
    }

    public static String getPluginId() {
        // returns the name defined in MANIFEST.MF (Bundle-SymbolicName)
        return getDefault().getBundle().getSymbolicName();
    }

    public static void logException(Throwable e) {
        if (e instanceof InvocationTargetException) {
            e = ((InvocationTargetException) e).getTargetException();
        }

        String message = null;
        IStatus status = null;

        if (e instanceof CoreException) {
            CoreException ce = (CoreException) e;
            status = new Status(ce.getStatus().getSeverity(), getPluginId(), ce.getMessage(), ce);
        } else {
            if (message == null)
                message = e.getMessage();
            if (message == null)
                message = e.toString();
            status = new Status(IStatus.ERROR, getPluginId(), IStatus.OK, message, e);
        }

        getDefault().getLog().log(status);
    }
}
