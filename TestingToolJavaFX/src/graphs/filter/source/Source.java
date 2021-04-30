package graphs.filter.source;

import java.util.List;

import graphs.paths.C1PPath;

public class Source implements SourceIF {
	private List<C1PPath> data;

	public Source(List<C1PPath> data) {
		this.data = data;
	}

	@Override
	public List<C1PPath> getData() {
		return this.data;
	}

}
