import static org.junit.Assert.*;

import org.junit.Test;

import com.project.LibraryLocator.server.DataParseImpl;
import com.project.LibraryLocator.shared.Library;


public class ParseTest {
	Library L1 = new Library("BABM001", "Alert Bay Public Library","Alert Bay", "(250) 974-5721","118 Fir Street", "Alert Bay", "V0N 1A0",50.577201,-126.90657);

	@Test
	public void test() {
		DataParseImpl parse= new DataParseImpl();
		assertEquals(L1,parse.parseLibrary().get(0));
		assertEquals(247, parse.parseLibrary().size());
		
		
	}

}
