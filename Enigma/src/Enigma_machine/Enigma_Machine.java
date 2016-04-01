package Enigma_machine;

public class Enigma_Machine {
	private static final int ASCII_VALUE_A = 65;							// Test 4
	private static final int ASCII_VALUE_0 = 48;							// Test 1
	Rotor[] rotor_blok = new Rotor[3]; 										// Test 1
	Reflector reflector = new Reflector();									// Test 3
			
	public void setup_rotor_blok(String volgorde_rotoren, String Start_Positie) { // Test 1
		Rotor previous_rotor = null;
		for (int i = 0; i <3 ; i++){
			int rotor_type_volgnummer = (int)volgorde_rotoren.charAt(i) - ASCII_VALUE_0 - 1;
			rotor_blok[i] = new Rotor(rotor_type_volgnummer, Start_Positie.charAt(i), previous_rotor);
			previous_rotor = rotor_blok[i];
		}
	}
	
	public char encode(char input_char){         							// Test 4
		int input_position = (int) input_char - ASCII_VALUE_A;							
		rotor_blok[2].rotate_rotor();							 							
		return get_output_char(reflector_to_output(reflector.reflector_result(input_to_reflector(input_position))));							
		}

	public String string_encode(String input_string){						// Test 5
		String result = "";
		char character_to_encode;
		for (int i=0; i < input_string.length(); i++){
			character_to_encode = input_string.charAt(i);
			if (character_to_encode == ' ')
				result += character_to_encode;
			else
				result += encode(input_string.charAt(i));							
		}
		return result;
	}

	public int input_to_reflector(int input_rotor){							// Test 4
		for (int i = 2 ; i>=0; i--)
			input_rotor = rotor_blok[i].encode_right_left(input_rotor);
		return input_rotor;
		// Dit kan ook geschreven worden als 
	    // 		input_rotor = rotor_blok[0].encode_right_left(rotor_blok[1].encode_right_left(rotor_blok[2].encode_right_left(input_rotor)))
		// maar bovenste is netter en er kunnen eventueel ook meer rotoren in het blok gezet worden.
	}

	public int reflector_to_output(int input_rotor){						// Test 4
		for (int i =0 ; i<3; i++)
			input_rotor = rotor_blok[i].encode_left_right(input_rotor);
		return input_rotor;
	}

	public char get_output_char(int input_pos){								// Test 4
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(input_pos);
	}

}