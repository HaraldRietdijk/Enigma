package Enigma_machine;

import java.util.HashMap;
import java.util.Map;

public class Rotor {
	private static final String[] ROTOR_CODERING = {"EKMFLGDQVZNTOWYHXUSPAIBRCJ","AJDKSIRUXBLHWTMCQGZNPYFVOE","BDFHJLCPRTXVZNYEIWGAKMUSQO"};
	private static final int[] ROTOR_ROLLOVER_POINT = {17,5,22};		// Test 1
	private String rechts;												// Test 2
	private String links = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";				// Test 2
	private int rollover_point;											// Test 1
	private int window_position = 0; 									// Test 0
	public Rotor rotor_to_left = null;									// Test 1
	Rotor(){}															// voor Test 0 na Test 1
	
	Rotor(int rotor_type_volgnummer, char start_positie, Rotor left_rotor){ 									// Test 1
		rechts = ROTOR_CODERING[rotor_type_volgnummer];
		rollover_point = ROTOR_ROLLOVER_POINT[rotor_type_volgnummer];
		window_position = links.indexOf(start_positie);
		rotor_to_left = left_rotor;
	}
	public int get_window_position(){  									// Test 0
		return window_position;
	}
	public void rotate_rotor(){											// Test 0
		window_position = (window_position +1) % 26;
		if ((rotor_to_left != null) && rollover_point_reached())		// Test 1
			rotor_to_left.rotate_rotor();		
	}
	public boolean rollover_point_reached(){							// Test 1
		return (window_position == rollover_point);
	}
	public int encode_right_left(int input_position){					// Test 2
		return verbind(rechts, links, input_position);
	}
	public int encode_left_right(int input_position){					// Test 2
		return verbind(links, rechts, input_position);
	}
	private int verbind(String van, String naar, int input_position){	// Test 2
		char input_char = van.charAt((input_position + window_position) % 26);
		return (naar.indexOf(input_char) -  window_position + 26) % 26;
	}
}
