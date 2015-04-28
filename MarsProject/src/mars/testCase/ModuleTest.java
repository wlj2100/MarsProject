package mars.testCase;

import static org.junit.Assert.*;
import mars.client.Module;

import org.junit.Test;

public class ModuleTest {
	Module module1 = new Module(1, 1, 1, 1, 1);

	@Test
	public void testAccessor() {
		assertEquals(1, module1.getCode());
		assertEquals(1, module1.getStatus());
		assertEquals(1, module1.getTurns());
		assertEquals(1, module1.getX());
		assertEquals(1, module1.getY());
	}

	@Test
	public void testMutator() {
		module1.setCode(2);
		module1.setStatus(2);
		module1.setTurns(2);
		module1.setXcoord(2);
		module1.setYcoord(2);
		assertEquals(2, module1.getCode());
		assertEquals(2, module1.getStatus());
		assertEquals(2, module1.getTurns());
		assertEquals(2, module1.getX());
		assertEquals(2, module1.getY());
	}

}