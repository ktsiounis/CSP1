import java.util.Arrays;
import java.util.Random;

/**
 * Created by dtsiounis on 12/04/2017.
 */
public class SimulatedAnnealing {

    private final static int N = 8;
    private static Random random = new Random();

    // Calculate the acceptance probability
    private static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        else if (newEnergy == energy) {
            return 0;
        }
        else {
            // If the new solution is worse, calculate an acceptance probability
            return Math.exp((energy - newEnergy) / temperature);
        }
    }

    //function about cost calculation
    private static int calculateCost(int[][] grid, int i, int j){
        int cost = 0, counteri = 0, counterj = 0;

        for(int k=0; k<N; k++){
            if(grid[k][j] == grid[i][j] && grid[i][j] != 0){
                counteri++;
            }
            if(grid[i][k] == grid[i][j] && grid[i][j] != 0){
                counterj++;
            }
        }

        if(counteri >= 2){
            cost += counteri*5;
        }
        if(counterj >= 2){
            cost += counterj*5;
        }

        if(i==0 && j==0) {
            if (grid[i][j] == 0 && grid[i + 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j + 1] == 0) {
                cost += 20;
            }
        }
        else if(i==N-1 && j==N-1){
            if(grid[i][j] == 0 && grid[i-1][j] == 0){
                cost+=20;
            }
            if(grid[i][j] == 0 && grid[i][j-1] == 0){
                cost+=20;
            }
        }
        else if(i==0 && j==N-1){
            if (grid[i][j] == 0 && grid[i + 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j - 1] == 0) {
                cost += 20;
            }
        }
        else if(i==N-1 && j==0){
            if (grid[i][j] == 0 && grid[i - 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j + 1] == 0) {
                cost += 20;
            }
        }
        else if(i<N-1 && i>0 && j == N-1){
            if (grid[i][j] == 0 && grid[i + 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j - 1] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i - 1][j] == 0) {
                cost += 20;
            }
        }
        else if(i<N-1 && i>0 && j==0){
            if (grid[i][j] == 0 && grid[i + 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j + 1] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i - 1][j] == 0) {
                cost += 20;
            }
        }
        else if(i==0 && j>0 && j<N-1){
            if (grid[i][j] == 0 && grid[i+1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j-1] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j+1] == 0) {
                cost += 20;
            }
        }
        else if(i==N-1 && j>0 && j<N-1){
            if (grid[i][j] == 0 && grid[i - 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j - 1] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j+1] == 0) {
                cost += 20;
            }
        }
        else{
            if (grid[i][j] == 0 && grid[i - 1][j] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j - 1] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i][j+1] == 0) {
                cost += 20;
            }
            if (grid[i][j] == 0 && grid[i+1][j] == 0) {
                cost += 20;
            }
        }

        return cost;
    }

    private static void printSolution(int[][] solution){
        System.out.println(" ---------------------------------");
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                System.out.print(" | " + solution[i][j]);
            }
            System.out.println(" |");
            System.out.println(" ---------------------------------");
        }
    }

    private static int[][] copyOfArray(int[][] initialArray){
        int[][] newArray = new int[N][N];

        for (int i=0; i<N; i++){
            System.arraycopy(initialArray[i], 0, newArray[i], 0, N);
        }

        return newArray;
    }

    public static void main(String[] args) {

        double T = 1.0;
        int tries = 0, totalCost = 10, i, j;
        int[][] grid = {
                {4,8,1,6,3,2,5,7},
                {3,6,7,2,1,6,5,4},
                {2,3,4,8,2,8,6,1},
                {4,1,6,5,7,7,3,5},
                {7,2,3,1,8,5,1,2},
                {3,5,6,7,3,1,8,4},
                {6,4,2,3,5,4,7,8},
                {8,7,1,4,2,3,5,6}
        };
        int[][] currentSolution;


        while( (T > 0.1) && (tries <= 6*N*N) && totalCost!=0){
            for(int k=0; k<2*N*N; k++) {

                i = random.nextInt(N);
                j = random.nextInt(N);

                currentSolution = copyOfArray(grid);
                currentSolution[i][j] = 0;

                int currentEnergy = calculateCost(grid, i, j);
                int newEnergy = calculateCost(currentSolution, i, j);

                System.out.println("i = " + i + " j = " + j + " num=" + grid[i][j]);
                System.out.println(currentEnergy + ">" + newEnergy);

                if (acceptanceProbability(currentEnergy, newEnergy, T) > Math.random()) {
                    grid = copyOfArray(currentSolution);
                    tries = 0;
                } else {
                    tries++;
                }
            }

            T *= 0.999;
            System.out.println(T);
        }

        printSolution(grid);
    }
}
