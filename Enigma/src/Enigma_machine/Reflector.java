package Enigma_machine;

public class Reflector {
	String reflector = "ABCDEFGDIJKGMKMIEBFTCVVJAT";						// Test 3     --- De reflector is van te voren vastgelegd.

	public int reflector_result(int input_position){						// Test 3
		char reflector_in = reflector.charAt(input_position);
		int first = reflector.indexOf(reflector_in);
		int last = reflector.lastIndexOf(reflector_in);
		if (input_position == first)
			return last;
		else 
			return first;
	}
}
