package Basic;
import java.awt.Dimension;

public class BasicCalculate {

	protected Dimension screensize;
	
	public BasicCalculate(Dimension screensize) {
		this.screensize = screensize;
	}

	public Dimension getScreensize() {
		return screensize;
	}

	public void setScreensize(Dimension screensize) {
		this.screensize = screensize;
	}

	public int calculateHeight(float y) {
		return (int)(screensize.height * y / 100);
	}
	
	public int calculateWidth(float x) {
		return (int)(screensize.width * x / 100);
	}
}
