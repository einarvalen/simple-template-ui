package org.fyfa.components;

import org.fyfa.Context;
import org.fyfa.Operation;

/** Used to supply a Form with directions on how to behave.
 *
 * @author EinarValen@gmail.com */
public class FormParams<T> extends ComponentParams<T> {
	private Operation operation;
	private String htmlMethod;
	private String formActionUri;

	public FormParams( String componentName, Class<T> classOfDto, Context context ) {
		super( componentName, classOfDto, context );
		this.htmlMethod = "POST";
		this.operation = Operation.New;
		this.formActionUri = "";
	}

	public Operation getOperation() {
		return operation;
	}

	public String getHtmlMethod() {
		return htmlMethod;
	}

	public String getFormActionUri() {
		return formActionUri;
	}

	public void setOperation( Operation operation ) {
		this.operation = operation;
	}

	/** GET, PUT, POST, DELETE */
	public void setHtmlMethod( String htmlMethod ) {
		this.htmlMethod = htmlMethod;
	}

	/** Submit action */
	public void setFormActionUri( String formActionUri ) {
		this.formActionUri = formActionUri;
	}
}
