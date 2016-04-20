package com.tools.kf.view;

//源自xutil3.0
/*package*/final class ViewInfo {
	public Object value;
	public int parentId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ViewInfo))
			return false;

		ViewInfo that = (ViewInfo) o;

		if (parentId != that.parentId)
			return false;
		if (value == null)
			return (null == that.value);

		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		int result = value.hashCode();
		result = 31 * result + parentId;
		return result;
	}
}
