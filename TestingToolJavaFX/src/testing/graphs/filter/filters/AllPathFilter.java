package testing.graphs.filter.filters;

import java.util.List;

import testing.graphs.filter.AbsPathFilter;
import testing.graphs.filter.source.SourceIF;
import testing.graphs.paths.C1PPath;

public class AllPathFilter extends AbsPathFilter{

	public AllPathFilter(SourceIF source) {
		super(source);
	}

	@Override
	public List<C1PPath> getData() {
		return this.source.getData();
	}
}
