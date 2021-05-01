package testing.results;

import java.util.Collections;

import javax.swing.JOptionPane;

public class NullResult extends TestResult{

	public NullResult() {
		super(Collections.emptyList());
	}

	@Override
	public void display() {
		JOptionPane.showMessageDialog(null, "The test failed (invalid inputs?)");
	}

}
