package io.picsou.util;

import java.util.List;

public class DataTable<T> {

	private  List<T> data;

	private FieldError fieldErrors;
	
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public FieldError getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(FieldError fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	
	
}
