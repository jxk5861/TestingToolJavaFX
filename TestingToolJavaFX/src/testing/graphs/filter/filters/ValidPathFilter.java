package testing.graphs.filter.filters;

import java.util.List;
import java.util.stream.Collectors;

import testing.graphs.filter.AbsPathFilter;
import testing.graphs.filter.source.SourceIF;
import testing.graphs.paths.C1PPath;

public class ValidPathFilter extends AbsPathFilter{

	public ValidPathFilter(SourceIF source) {
		super(source);
	}

	@Override
	public List<C1PPath> getData() {
		return this.source.getData().stream().filter(C1PPath::succeeds).collect(Collectors.toList());
	}
}
