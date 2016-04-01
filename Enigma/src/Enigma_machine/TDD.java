package Enigma_machine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TDD {
	private Enigma_Machine enigma;
	
	@Before
	public void set_up_machine(){									// vanaf test_1
		enigma = new Enigma_Machine();
		enigma.setup_rotor_blok("123", "MCK");
	}
	@Test
	public void test_0(){ 											// Eerst maar eens een rotor laten draaien
		Rotor rotor = new Rotor();	
		assertEquals(rotor.get_window_position(),0);
		rotor.rotate_rotor();
		assertEquals(rotor.get_window_position(),1);
		for (int i = 2; i<26; i++){									// Een rondje draaien
			rotor.rotate_rotor();
			assertEquals(rotor.get_window_position(),i);
		}
		rotor.rotate_rotor();										// Helemaal rond
		assertEquals(rotor.get_window_position(),0);		
	}
	@Test
	public void test_1() { 											// Drie rotoren in een doosje plaatsen en met elkaar laten draaien
		assertEquals(enigma.rotor_blok[0].get_window_position(),12);// Ga uit van de setup I-II-III en codering MCK
		assertEquals(enigma.rotor_blok[1].get_window_position(),2);	
		assertEquals(enigma.rotor_blok[2].get_window_position(),10);
		enigma.rotor_blok[2].rotate_rotor();;						// Alleen rotor 2 draait
		assertEquals(enigma.rotor_blok[0].get_window_position(),12);
		assertEquals(enigma.rotor_blok[1].get_window_position(),2);
		assertEquals(enigma.rotor_blok[2].get_window_position(),11);
		for (int i = 12; i < 22; i++){								// Rotor 2 tot aan het rollover punt draaien
			enigma.rotor_blok[2].rotate_rotor();
			assertEquals(enigma.rotor_blok[0].get_window_position(),12);
			assertEquals(enigma.rotor_blok[1].get_window_position(),2);
			assertEquals(enigma.rotor_blok[2].get_window_position(),i);
		}
		enigma.rotor_blok[2].rotate_rotor();						// Rollover op rotor 1
		assertEquals(enigma.rotor_blok[0].get_window_position(),12);	
		assertEquals(enigma.rotor_blok[1].get_window_position(),3);
		assertEquals(enigma.rotor_blok[2].get_window_position(),22);
		for (int i = 23; i < 27; i++)								// rotor 2 tot aan de laatste positie + 1 draaien
			enigma.rotor_blok[2].rotate_rotor();
		assertEquals(enigma.rotor_blok[0].get_window_position(),12);
		assertEquals(enigma.rotor_blok[1].get_window_position(),3);
		assertEquals(enigma.rotor_blok[2].get_window_position(),0);
		for (int i = 0; i < 52; i++)								// rollover op rotor 0 (en dus twee keer op rotor 1)
			enigma.rotor_blok[2].rotate_rotor();
		assertEquals(enigma.rotor_blok[0].get_window_position(),13);
		assertEquals(enigma.rotor_blok[1].get_window_position(),5);
		assertEquals(enigma.rotor_blok[2].get_window_position(),0);
	}
	@Test
	public void test_2(){ 											// Een rotor laten coderen, maak gebruik van rotor_III met de 'K' in het window
		assertEquals(enigma.rotor_blok[2].encode_right_left(5),20); // positie 5 rechts is 'E', 'E' in de linkerkolom staat op pos 20
		assertEquals(enigma.rotor_blok[2].encode_left_right(14),4); // positie 14 links is 'Y', rechts staat die op de positie 4
		enigma.rotor_blok[2].rotate_rotor();						// Draai de rotor een slag, 'L' staat nu in het window (=positie 0)
		assertEquals(enigma.rotor_blok[2].encode_right_left(5),23); // positie 5 rechts is nu 'I', 'I' in de linkerkolom staat nu op pos 23
		assertEquals(enigma.rotor_blok[2].encode_left_right(14),1); // positie 14 links is 'Z', rechts staat die op de positie 1
	}
	@Test
	public void test_3() { 											// Een reflector maken
		Reflector reflector = new Reflector();
		assertEquals(reflector.reflector_result(4),16);
		assertEquals(reflector.reflector_result(16),4);
	}
	@Test
	public void test_4() { 											// Volledige codering van een letter
		assertEquals(enigma.encode('E'),'Q');						// Controleer de output
		assertEquals(enigma.rotor_blok[0].get_window_position(),12);// en de rotor zetting
		assertEquals(enigma.rotor_blok[1].get_window_position(),2);
		assertEquals(enigma.rotor_blok[2].get_window_position(),11);
		assertEquals(enigma.encode('N'),'M');						// Volgende letter
		assertEquals(enigma.rotor_blok[0].get_window_position(),12);// Rotor moet weer gedraaid zijn
		assertEquals(enigma.rotor_blok[1].get_window_position(),2);
		assertEquals(enigma.rotor_blok[2].get_window_position(),12);
	}
	@Test
	public void test_5(){			 								// Een string coderen
		assertEquals(enigma.string_encode("ENIGMA REVEALED"),"QMJIDO MZWZJFJR");
	}
}
