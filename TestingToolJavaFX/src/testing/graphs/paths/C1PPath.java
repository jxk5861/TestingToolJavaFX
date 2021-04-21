package testing.graphs.paths;

import java.util.LinkedList;

import testing.graphs.Vertex;

public class C1PPath extends GraphPath{
	private boolean valid;

	public C1PPath(LinkedList<Vertex> path, boolean valid) {
		super(path);
		this.valid = valid;
	}

	public boolean succeeds() {
		return valid;
	}

	public boolean fails() {
		return !valid;
	}

	public void print() {
		if (this.valid) {
			System.out.println(this);
		} else {
			System.err.println(this);
		}
	}

	@Override
	public String toString() {
		if (path.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		builder.append(super.toString());
		builder.append(" (");
		builder.append(this.valid ? "success" : "fail");
		builder.append(")");

		return builder.toString();
	}
}
