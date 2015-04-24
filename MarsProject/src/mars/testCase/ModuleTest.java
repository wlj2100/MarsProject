package mars.testCase;

import static org.junit.Assert.*;
import mars.client.Module;

import org.junit.Test;

public class ModuleTest {
        Module module1 = new Module(1,1,1,1,1);
        Module module2;
        @Test
        public void testAccessor() {
                assertEquals(1,module1.getCode());
                assertEquals(1,module1.getStatus());
                assertEquals(1,module1.getTurns());
                assertEquals(1,module1.getX());
                assertEquals(1,module1.getY());
        }
        @Test
        public void testStringCon() {
                
                String moduleString = module1.toString();
                System.out.println(moduleString);
                module2 = new Module(moduleString);              
                assertEquals(1,module2.getCode());
                assertEquals(1,module2.getStatus());
                assertEquals(1,module2.getTurns());
                assertEquals(1,module2.getX());
                assertEquals(1,module2.getY());
        }

}