package testing.graphs.filter;

import testing.graphs.filter.source.SourceIF;

public abstract class AbsPathFilter implements SourceIF {
	protected SourceIF source;
	
	public AbsPathFilter(SourceIF source) {
		this.source = source;
	}
}
