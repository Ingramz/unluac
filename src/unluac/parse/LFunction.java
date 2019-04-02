package unluac.parse;

public class LFunction extends BObject {
  
  public BHeader header;
  public String name;
  public int linedefined;
  public int lastlinedefined;
  public LFunction parent;
  public int[] code;
  public BList<BInteger> lines;
  public LLocal[] locals;
  public LObject[] constants;
  public LUpvalue[] upvalues;
  public LFunction[] functions;
  public int maximumStackSize;
  public int numUpvalues;
  public int numParams;
  public int vararg;
  public boolean stripped;
  
  public LFunction(BHeader header, String name, int linedefined, int lastlinedefined, int[] code, BList<BInteger> lines, LLocal[] locals, LObject[] constants, LUpvalue[] upvalues, LFunction[] functions, int maximumStackSize, int numUpValues, int numParams, int vararg) {
    this.header = header;
    this.name = name;
    this.linedefined = linedefined;
    this.lastlinedefined = lastlinedefined;
    this.code = code;
    this.lines = lines;
    this.locals = locals;
    this.constants = constants;
    this.upvalues = upvalues;
    this.functions = functions;
    this.maximumStackSize = maximumStackSize;
    this.numUpvalues = numUpValues;
    this.numParams = numParams;
    this.vararg = vararg;
    this.stripped = false;
  }
  
}
