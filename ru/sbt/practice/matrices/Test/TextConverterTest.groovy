package ru.sbt.practice.matrices.Test
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.sbt.practice.matrices.TextProcessing.TextConverter
/**
 * Created by artem on 03.03.15.
 */
class TextConverterTest extends GroovyTestCase {
    private text2 = "тающий снимающий жующий"
    private text = "The libraries for JUnit and TestNG are shipped with IntelliJ IDEA, but are not included in the classpath of your project or module by default. Consequently, when a test class is created, the references to the TestCase class or test annotations are not resolved.";
    @Before
    void setUp() {
        super.setUp()
    }
    @After
    void tearDown() {

    }
    @Test
    void testStemText() {
        String[] ss = TextConverter.stemText(text2);
        for (String v : ss) {
            print(v+ " ")
        }

    }
}
