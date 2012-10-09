package org.fyfa.samples.link;

/** A holding tank for URIs
 *
 * @author EinarValen@gmail.com */
public class PathSetting {
	private final String baseUri;
	String searchUri, newUri, modifyUri, deleteUri, viewUri;
	String tableUri, searchFormUri, newFormUri, modifyFormUri, viewFormUri;

	public PathSetting( String baseUri ) {
		this.baseUri = baseUri;
	}

	private String complete( String uri ) {
		return baseUri + uri.replace( '{', '$' ).replace( '}', '$' );
	}

	public String getSearchUri() {
		return searchUri;
	}

	public void setSearchUri( String searchUri ) {
		this.searchUri = complete( searchUri );
	}

	public String getNewUri() {
		return newUri;
	}

	public void setNewUri( String newUri ) {
		this.newUri = complete( newUri );
	}

	public String getModifyUri() {
		return modifyUri;
	}

	public void setModifyUri( String modifyUri ) {
		this.modifyUri = complete( modifyUri );
	}

	public String getDeleteUri() {
		return deleteUri;
	}

	public void setDeleteUri( String deleteUri ) {
		this.deleteUri = complete( deleteUri );
	}

	public String getViewUri() {
		return viewUri;
	}

	public void setViewUri( String viewUri ) {
		this.viewUri = complete( viewUri );
	}

	public String getTableUri() {
		return tableUri;
	}

	public void setTableUri( String tableUri ) {
		this.tableUri = complete( tableUri );
	}

	public String getSearchFormUri() {
		return searchFormUri;
	}

	public void setSearchFormUri( String searchFormUri ) {
		this.searchFormUri = complete( searchFormUri );
	}

	public String getNewFormUri() {
		return newFormUri;
	}

	public void setNewFormUri( String newFormUri ) {
		this.newFormUri = complete( newFormUri );
	}

	public String getModifyFormUri() {
		return modifyFormUri;
	}

	public void setModifyFormUri( String modifyFormUri ) {
		this.modifyFormUri = complete( modifyFormUri );
	}

	public String getViewFormUri() {
		return viewFormUri;
	}

	public void setViewFormUri( String viewFormUri ) {
		this.viewFormUri = complete( viewFormUri );
	}

	public String getBaseUri() {
		return baseUri;
	}
}
