package graphs.filter;

import graphs.filter.source.SourceIF;

public abstract class AbsPathFilter implements SourceIF {
	protected SourceIF source;
	
	public AbsPathFilter(SourceIF source) {
		this.source = source;
	}
}
