package testing.results;

import java.util.Collections;

import javax.swing.JOptionPane;

public class NullResult extends TestResult{
	private String message;
	
	public NullResult() {
		super(Collections.emptyList());
		this.message = "The test failed (invalid inputs?)";
	}
	
	public NullResult(String message) {
		super(Collections.emptyList());
		this.message = message;
	}

	@Override
	public void display() {
		JOptionPane.showMessageDialog(null, this.message);
	}

}
