package core.structural_patterns.facade;

/**
 * 
 * @ClassName: Computer
 * @Description: 外观模式：
 * 外观模式是为了解决类与类之家的依赖关系的，像spring一样，
 * 可以将类和类之间的关系配置到配置文件中，
 * 而外观模式就是将他们的关系放在一个Facade类中，
 * 降低了类类之间的耦合度，该模式中没有涉及到接口
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Computer {

	private Cpu cpu;
	private Disk disk;
	private Memory memory;
	
	public Computer(){
		cpu = new Cpu();
		disk = new Disk();
		memory = new Memory();
	}
	
	public void startUp(){
		cpu.startUp();
		memory.startUp();
		disk.startUp();
	}
	
	public void stutDown(){
		cpu.stutDown();
		memory.stutDown();
		disk.stutDown();
	}
}
