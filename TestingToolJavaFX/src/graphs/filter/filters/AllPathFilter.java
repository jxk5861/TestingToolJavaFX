package graphs.filter.filters;

import java.util.List;

import graphs.filter.AbsPathFilter;
import graphs.filter.source.SourceIF;
import graphs.paths.C1PPath;

public class AllPathFilter extends AbsPathFilter{

	public AllPathFilter(SourceIF source) {
		super(source);
	}

	@Override
	public List<C1PPath> getData() {
		return this.source.getData();
	}
}
