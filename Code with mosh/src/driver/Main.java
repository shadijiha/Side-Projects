/**
 *
 */

package driver;

public class Main {

	public static void main(String[] args) {

		final int SCORE = 550;
		final int SIGMA = 1440;
		final int MU = 74016;

		System.out.printf("P{X > %d} = \n", SCORE);

		double RHS = (float) (SCORE - MU) / (float) SIGMA;

		System.out.printf("P{Z > %f}\n", RHS);
		System.out.printf("1 - Phi(%f)\n", RHS);

	}
}
