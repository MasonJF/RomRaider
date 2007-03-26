package enginuity.NewGUI.etable;

public class ETableSaveState {
	private Double[][] internalData;
	//private String name;
	public ETableSaveState(Double[][] data){
		//this.name = name;
		int width = data.length;
		int height = data[0].length;
		
		//System.out.println("Dimensions:  w:"+ width+"   h:"+height);
		this.internalData = new Double[width][height];
		
		for(int i = 0; i < width; i ++){
			for(int j=0; j < height; j++){
				double tempData = data[i][j];
				this.internalData[i][j] = tempData;
			}
		}
		
		System.out.println("Sample: "+this.internalData[0][0]);
	}
	
	public Double[][] getData(){
		return this.internalData;
	}
	
	/*
	public String getName(){
		return name;
	}
	*/
}
