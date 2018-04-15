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
				if (!file.isDirectory()) {
					documentURI = new File(file.getParent()).toURI();
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
