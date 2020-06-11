package regression.branchenhancement.testcase;

import java.lang.reflect.Method;

import org.evosuite.Properties;
import org.evosuite.utils.MethodUtil;
import org.junit.Test;

import com.test.TestUility;

import evosuite.shell.EvoTestResult;

public class MultipleExceptionsExampleTest extends BranchEnhancementTestSetup {
	@Test
	public void testMultipleExceptionExample() {
		Class<?> clazz = regression.branchenhancement.example.MultipleExceptionsExample.class;

		String methodName = "test";
		int parameterNum = 2;

		String targetClass = clazz.getCanonicalName();
		Method method = TestUility.getTargetMethod(methodName, clazz, parameterNum);

		String targetMethod = method.getName() + MethodUtil.getSignature(method);
		String cp = "target/test-classes";

		String fitnessApproach = "fbranch";

		int timeBudget = 150;
		EvoTestResult resultT = null;
		EvoTestResult resultF = null;

		try {
			resultT = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
		} catch (Exception e1) {
			try {
				resultT = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
			} catch (Exception e2) {
				resultT = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
			}
		}

		Properties.ENABLE_BRANCH_ENHANCEMENT = false;
		try {
			resultF = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
		} catch (Exception e1) {
			try {
				resultF = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
			} catch (Exception e2) {
				resultF = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
			}
		}

		if (resultT == null) {
			resultT = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
		}

		if (resultF == null) {
			resultF = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
		}

		int ageT = resultT.getAge();
		int timeT = resultT.getTime();
		double coverageT = resultT.getCoverage();
		double coverageF = resultF.getCoverage();

		assert ageT <= 1900;
		assert timeT <= 100;
		assert coverageT == 1.0;
		assert coverageF < 1.0;
	}
}
