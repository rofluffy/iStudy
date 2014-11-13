import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.project.LibraryLocator.server.DataParseImpl;
import com.project.LibraryLocator.shared.Library;


public class ParseTest {
	Library L1 = new Library("BABM001", "Alert Bay Public Library","Alert Bay", "(250) 974-5721","118 Fir Street", "Alert Bay", "V0N 1A0",50.577201,-126.90657);
	String P1= "A1C 2E4";
	String P2= "A1C2E4";
	String P3= "A1 C2E4";
	String P4= "132fja";
	Double D1= 50.577201;
	Double D2= -150.577201;
	Double D3= 220.577201;

	@Test
	public void test() {
		DataParseImpl parse= new DataParseImpl();
		assertEquals(L1,parse.parseLibrary().get(0));
		assertEquals(247, parse.parseLibrary().size());
	}
	
	@Test 
	public void testVaildPostalCode(){
		assertTrue(L1.checkVaildPostalCode(P1));
		assertTrue(L1.checkVaildPostalCode(P2));
		assertTrue(L1.checkVaildPostalCode(P3));
		assertFalse(L1.checkVaildPostalCode(P4));
	}
	
	@Test 
	public void testValidLat(){
		assertTrue(L1.checkValidLat(D1));
		assertFalse(L1.checkValidLat(D2));
		assertTrue(L1.checkValidLng(D2));
		assertFalse(L1.checkValidLng(D3));
		
	}
	@Test
	public void testChar(){
		assertEquals('A', P1.toCharArray()[0]);
		assertTrue(Character.isDigit(P1.toCharArray()[1]));
		
	}

}
