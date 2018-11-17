package ui;

public class UIFa�ade {

	private static UIFa�ade self;
	
	private UICreator uiC;
	private UIScreen uiS;
	
	private UIFa�ade() {
	}
	
	public void connectionError() {
		uiC.serverNotFound();
	}

	public void connectionDone() {
		uiC.close();
		uiS.start();
	}
	
	public void initalize(UICreator uiC, UIScreen uiS) {
		this.uiC = uiC;
		this.uiS = uiS;
	}
	
	public static synchronized UIFa�ade getInstance() {
		if (self == null) {
			self = new UIFa�ade();
		}
		return self;
	}
	
}
