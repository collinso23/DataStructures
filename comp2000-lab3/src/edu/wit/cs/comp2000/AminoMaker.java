package edu.wit.cs.comp2000;

/* Generates all permutations of RNA triplets to encode amino acids
 * 
 * Wentworth Institute of Technology
 * COMP 2000
 * Lab 3
 * 
 */
public class AminoMaker {

	public static void main(String[] args) {

		BagInterface<String> aminoBag = new ArrayBag<>(64);

		makeAminos(aminoBag);

		// print out all strings in bag with line number
		int lineCount = 1;
		while (!aminoBag.isEmpty())
			System.out.printf(aminoBag.remove() + " %s \n",lineCount++);
	}

	/** Initialize parameters and call recursive method to fill bag.
    	@param aminoBag  The bag to fill with RNA triplets
    	@return  Nothing: filling the bag is enough. */
	public static void makeAminos(BagInterface<String> aminoBag) {
		String rna = "";
		_genAminos(aminoBag, rna);
	}

	/**
	 * Write a private recursive method to perform the string construction
	 */
	private static void _genAminos(BagInterface<String> aminoBag, String rna) {
		if (rna.length()==3)
			aminoBag.add(rna);
		else {
			String seqU = rna+"U";
			_genAminos(aminoBag,seqU);

			String seqC = rna+"C";
			_genAminos(aminoBag,seqC);

			String seqG = rna+"G";
			_genAminos(aminoBag,seqG);

			String seqA = rna+"A";
			_genAminos(aminoBag,seqA);
		}
	}
}
