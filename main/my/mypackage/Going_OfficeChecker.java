package my.mypackage;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.Verdict;
import net.kingder.utils.io.MyInputReader;

import java.util.Collection;
import java.util.Collections;

public class Going_OfficeChecker {
	public Verdict check(MyInputReader input, MyInputReader expected, MyInputReader actual) {
		return Verdict.UNDECIDED;
	}

	public double getCertainty() {
		return 0;
	}

	public Collection<? extends Test> generateTests() {
		return Collections.emptyList();
	}
}