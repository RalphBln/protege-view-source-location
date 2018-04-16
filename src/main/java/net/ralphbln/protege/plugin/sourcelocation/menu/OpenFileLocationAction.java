package net.ralphbln.protege.plugin.sourcelocation.menu;

import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class OpenFileLocationAction extends ProtegeOWLAction {

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	public void actionPerformed(ActionEvent event) {
		OWLOntology activeOntology = getOWLModelManager().getActiveOntology();
		IRI documentIRI = activeOntology.getOWLOntologyManager().getOntologyDocumentIRI(activeOntology);
		URI documentURI = documentIRI.toURI();

		String scheme = documentURI.getScheme();
		if (scheme == null || scheme.equalsIgnoreCase("file")) {
			File file = new File(documentURI);
			if (file.exists()) {

				if (System.getProperty("os.name").toLowerCase().contains("mac")) {
					// on a Mac, we have even nicer options

					String[] cmdArray;

					File workingDirectory;

					if (file.isDirectory()) {
						// should never happen, but just in case
						workingDirectory = file;
						cmdArray = new String[] {"open", file.getAbsolutePath()};
					} else {
						workingDirectory = file.getParentFile();
						cmdArray = new String[] {"open", "-R", file.getAbsolutePath()};
					}
					try {
						Runtime.getRuntime().exec(cmdArray, null, workingDirectory);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						return;
					}
				} else {
					if (!file.isDirectory()) {
						// should always be a file, but just in case
						documentURI = new File(file.getParent()).toURI();
					}
				}
			}
		}

		try {
			Desktop.getDesktop().browse(documentURI);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
