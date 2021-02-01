package unluac.test;

import java.io.IOException;

public class RunTest {

  public static void main(String[] args) throws IOException {
    LuaSpec spec = new LuaSpec(0x54);
    UnluacSpec uspec = new UnluacSpec();
    //uspec.disassemble = true;
    if(TestFiles.suite.run(spec, uspec, args[0], false)) {
      System.exit(0);
    } else {
      System.exit(1);
    }
  }
}
