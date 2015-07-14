import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Config {

	SimulationEngine.EdgeMode edgeMode;
	private Cell[][] cells;

	public Config(SimulationEngine.EdgeMode edgeMode) {

		this.edgeMode = edgeMode;
	}
	//checks if rectangular input and counts rows and columns
	//Zeichenüberprüfung auch hier? Ansatz--> neues try/catch nach finally und nochmals Zeichen für Zeichen durch gehen
	public int [] checkInputfile (String filename){
		int rows, columns, buffercolumns;
		int[] size = new int[2];
		
		String inputline = null;
		String inputfield = null;
		Scanner scanner = null;
		
		rows= 0;
		columns = 0;
		buffercolumns = 0;

		try {
			//build Scanner to read inputfile
			scanner = new Scanner(new File(filename));
			
			//read and count lines of inputfile
			while (scanner.hasNextLine()){
				inputline = scanner.nextLine();
				
				//count charcters of line
				columns = inputline.toCharArray().length;
				rows++;
				
				//compare length of line to previous
				if (buffercolumns != columns && buffercolumns != 0){
					throw new IncorrectSizeException();
				}
				buffercolumns= columns;
			}

			size[0]= rows;
			size[1]= columns;
						
		} catch (FileNotFoundException e){ //weitere catches??
			e.printStackTrace();
		} catch (IncorrectSizeException e){
			
		}finally {
			scanner.close();
		}
		return size;
	}
	public Cell[][] getCellsFromFile(int []size) {
		
		return cells;
	}

}
