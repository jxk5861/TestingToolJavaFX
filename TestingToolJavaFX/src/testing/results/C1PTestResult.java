package testing.results;

import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import graphs.filter.AbsPathFilter;
import graphs.filter.filters.AllPathFilter;
import graphs.filter.filters.InvalidPathFilter;
import graphs.filter.filters.ValidPathFilter;
import graphs.filter.source.Source;
import graphs.filter.source.SourceIF;
import graphs.paths.C1PPath;

public class C1PTestResult extends TestResult{
	
	private String filterMode;
	private List<C1PPath> paths;

	public C1PTestResult(List<C1PPath> paths) {
		super(Collections.emptyList());
		this.paths = paths;
	}

	@Override
	public void display() {
		String[] choices = { "All Paths", "Valid Paths", "Invalid Paths" };
	    String input = (String) JOptionPane.showInputDialog(null, "Select a filter option",
	        "Filter Mode", JOptionPane.QUESTION_MESSAGE, null, choices, null);
		this.filterMode = input;
		
		// I do not think this is a problem since no methods are provided to add GraphPath to paths.
		List<C1PPath> paths = this.paths;
		
		SourceIF source = new Source(paths);
		AbsPathFilter filter;
		if(filterMode.equals("All Paths")) {
			filter = new AllPathFilter(source);
		} else if(filterMode.equals("Valid Paths")) {
			filter = new ValidPathFilter(source);
		} else {
			filter = new InvalidPathFilter(source);
		}
		
		
		long valid = paths.stream().filter(C1PPath::succeeds).count();
		long invalid = paths.stream().filter(C1PPath::fails).count();
		
		System.out.println("C1P Testing Results:");
		System.out.println(filter.getData().size() + " Total Paths (" + valid + " valid, " + invalid + " invalid)");
		filter.getData().forEach(C1PPath::print);
		
		// Sleep for a small amount so that the out/err print in the right order.
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

}
